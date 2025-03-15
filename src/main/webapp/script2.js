// Constants
const TEXT = 'text';
const URL = 'url';
const IMAGE = 'image';

let currentContentType = TEXT;
let imageData = null;
let editingContentId = null;

function startEditingNoteName() {
    const noteNameDisplay = document.getElementById('note-name-display');
    const noteNameEdit = document.getElementById('note-name-edit');
    const noteNameInput = document.getElementById('note-name-input');

    noteNameDisplay.classList.add('hidden');
    noteNameEdit.classList.remove('hidden');
    noteNameInput.value = title;
    noteNameInput.focus();
    noteNameInput.select();
}

function saveNoteName() {
    const noteNameInput = document.getElementById('note-name-input');

    const newTitle = noteNameInput.value.trim() || 'Untitled Note';

    // Create POST request
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = 'NameFiltered/ChangeNameStorageItem.html';

    const itemIdInput = document.createElement('input');
    itemIdInput.type = 'hidden';
    itemIdInput.name = 'id';
    itemIdInput.value = currentNoteId;

    const newNameInput = document.createElement('input');
    newNameInput.type = 'hidden';
    newNameInput.name = 'name';
    newNameInput.value = newTitle;

    form.appendChild(itemIdInput);
    form.appendChild(newNameInput);

    document.body.appendChild(form);
    form.submit();
}

function switchTab(type) {
    currentContentType = type;

    const contentTypeLabel = document.getElementById('content-type-label');
    contentTypeLabel.textContent = type.charAt(0).toUpperCase() + type.slice(1);

    const tabButtons = document.querySelectorAll('.tab-button');
    const tabPanes = document.querySelectorAll('.tab-pane');

    tabButtons.forEach(button => {
        if (button.dataset.type === type) {
            button.classList.add('active');
        } else {
            button.classList.remove('active');
        }
    });

    tabPanes.forEach(pane => {
        if (pane.id === `${type}-tab`) {
            pane.classList.add('active');
        } else {
            pane.classList.remove('active');
        }
    });

    if (type !== IMAGE) {
        clearImagePreview();
    }
}

function getContentFromWebsite()
{
    let content = '';
    switch (currentContentType) {
        case TEXT:
            content = document.getElementById('text-input').value.trim();
            if (!content) return;
            document.getElementById('text-input').value = '';
            break;

        case URL:
            content = document.getElementById('url-input').value.trim();
            if (!content) return;
            document.getElementById('url-input').value = '';
            break;

        case IMAGE:
            if (imageData) {
                content = imageData; // Use the base64 data
            } else {
                content = document.getElementById('image-url-input').value.trim();
                if (!content) return;
            }
            document.getElementById('image-url-input').value = '';
            clearImagePreview();
            break;
    }
    return content;
}

function addContent() {
    if (editingContentId) {
        updateContent();
        return;
    }
    let content = getContentFromWebsite() ;
    addContentToNote(content);
}

function addContentToNote(content)
{
    // Create a form for POST request
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = '/AddContentToNote.html';

    const itemIdInput = document.createElement('input');
    itemIdInput.type = 'hidden';
    itemIdInput.name = 'id';
    itemIdInput.value = currentNoteId;

    const newTypeInput = document.createElement('input');
    newTypeInput.type = 'hidden';
    newTypeInput.name = 'type';
    newTypeInput.value = currentContentType;

    const newValueInput = document.createElement('input');
    newValueInput.type = 'hidden';
    newValueInput.name = 'value';
    newValueInput.value = content;

    form.appendChild(itemIdInput);
    form.appendChild(newTypeInput);
    form.appendChild(newValueInput) ;

    document.body.appendChild(form);
    form.submit();
}

function startEditingContent(id) {

    const contentItem = contents.find(item => item.id === id);
    if (!contentItem) return;

    editingContentId = id;

    switchTab(contentItem.type);

    switch (contentItem.type) {
        case TEXT:
            document.getElementById('text-input').value = contentItem.content;
            break;

        case URL:
            document.getElementById('url-input').value = contentItem.content;
            break;

        case IMAGE:
            if (contentItem.content.startsWith('data:image')) {
                // base64 image
                imageData = contentItem.content;
                showImagePreview(contentItem.content);
            } else {
                // URL
                document.getElementById('image-url-input').value = contentItem.content;
            }
            break;
    }

    const addContentButton = document.getElementById('add-content-button');
    addContentButton.innerHTML = '<i class="fas fa-save"></i> Update ' + contentItem.type.charAt(0).toUpperCase() + contentItem.type.slice(1);

    const cancelButton = document.getElementById('cancel-edit-button');
    cancelButton.style.display = 'inline-block';

    document.querySelector('.add-content-section').classList.add('editing');

    document.querySelector('.add-content-section').scrollIntoView({ behavior: 'smooth' });
}

function updateContent() {
    if (!editingContentId) return;

    const contentItem = contents.find(item => item.id === editingContentId);
    if (!contentItem) return;

    let content = getContentFromWebsite() ;

    // Create a form for POST request
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = '/ChangeContent.html';

    const contentIdInput = document.createElement('input');
    contentIdInput.type = 'hidden';
    contentIdInput.name = 'id';
    contentIdInput.value = editingContentId;

    const noteIdInput = document.createElement('input');
    noteIdInput.type = 'hidden';
    noteIdInput.name = 'parentId';
    noteIdInput.value = currentNoteId;


    const valueInput = document.createElement('input');
    valueInput.type = 'hidden';
    valueInput.name = 'newValue';
    valueInput.value = content;

    form.appendChild(contentIdInput);
    form.appendChild(noteIdInput);
    form.appendChild(valueInput);

    cancelEditing();

    document.body.appendChild(form);
    form.submit();
}

function cancelEditing() {
    editingContentId = null;

    document.getElementById('text-input').value = '';
    document.getElementById('url-input').value = '';
    document.getElementById('image-url-input').value = '';
    clearImagePreview();

    const addContentButton = document.getElementById('add-content-button');
    addContentButton.innerHTML = '<i class="fas fa-plus"></i> Add <span id="content-type-label">' + currentContentType.charAt(0).toUpperCase() + currentContentType.slice(1) + '</span>';

    const cancelButton = document.getElementById('cancel-edit-button');
    cancelButton.style.display = 'none';

    document.querySelector('.add-content-section').classList.remove('editing');
}

function deleteContent(id) {
    window.location.replace('/DeleteContent.html?id=' + currentNoteId +
        '&contentId=' + id) ;
}

function renderContent(item) {
    const template = document.getElementById('content-item-template');
    const contentItem = template.content.cloneNode(true).querySelector('.content-item');

    contentItem.dataset.id = item.id;
    const contentBody = contentItem.querySelector('.content-body');

    switch (item.type) {
        case TEXT:
            // Add text-content class to preserve whitespace
            contentBody.classList.add('text-content');
            contentBody.textContent = item.content;
            break;

        case URL:
            const link = document.createElement('a');
            link.href = item.content;
            link.target = '_blank';
            link.rel = 'noopener noreferrer';

            const icon = document.createElement('i');
            icon.className = 'fas fa-link';

            link.appendChild(icon);
            link.appendChild(document.createTextNode(item.content));

            contentBody.appendChild(link);
            break;

        case IMAGE:
            const img = document.createElement('img');
            img.src = item.content;
            img.alt = 'Note image';
            contentBody.appendChild(img);
            break;
    }

    const deleteButton = contentItem.querySelector('.delete-btn');
    deleteButton.addEventListener('click', function() {
        deleteContent(item.id);
    });

    const editButton = document.createElement('button');
    editButton.className = 'edit-btn';
    editButton.innerHTML = '<i class="fas fa-edit"></i>';
    editButton.addEventListener('click', function(e) {
        e.stopPropagation();
        startEditingContent(item.id);
    });
    contentItem.appendChild(editButton);

    const noteContents = document.getElementById('note-contents');
    noteContents.appendChild(contentItem);
}

function updateEmptyNoteMessage() {
    const emptyNoteMessage = document.getElementById('empty-note-message');
    if (contents.length === 0) {
        emptyNoteMessage.classList.remove('hidden');
    } else {
        emptyNoteMessage.classList.add('hidden');
    }
}

function handleFileSelect(e) {
    const file = e.target.files[0];
    if (file && file.type.startsWith('image/')) {
        processImageFile(file);
    }
}

function processImageFile(file) {
    // Convert file to base64
    const reader = new FileReader();

    reader.onload = function(e) {
        imageData = e.target.result;
        showImagePreview(imageData);
    };

    reader.readAsDataURL(file);
}

function showImagePreview(src) {
    const imageDropArea = document.getElementById('image-drop-area');
    const imagePreview = document.getElementById('image-preview');
    const previewImage = document.getElementById('preview-image');

    previewImage.src = src;
    imageDropArea.classList.add('hidden');
    imagePreview.classList.remove('hidden');
}

function clearImagePreview() {
    const imageDropArea = document.getElementById('image-drop-area');
    const imagePreview = document.getElementById('image-preview');
    const previewImage = document.getElementById('preview-image');

    imageData = null;
    previewImage.src = '';
    imageDropArea.classList.remove('hidden');
    imagePreview.classList.add('hidden');
}

function setupDragAndDrop() {
    const imageDropArea = document.getElementById('image-drop-area');

    ['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
        imageDropArea.addEventListener(eventName, preventDefaults, false);
    });

    ['dragenter', 'dragover'].forEach(eventName => {
        imageDropArea.addEventListener(eventName, highlight, false);
    });

    ['dragleave', 'drop'].forEach(eventName => {
        imageDropArea.addEventListener(eventName, unhighlight, false);
    });

    imageDropArea.addEventListener('drop', handleDrop, false);

    function preventDefaults(e) {
        e.preventDefault();
        e.stopPropagation();
    }

    function highlight() {
        imageDropArea.classList.add('active');
    }

    function unhighlight() {
        imageDropArea.classList.remove('active');
    }

    function handleDrop(e) {
        const dt = e.dataTransfer;
        const file = dt.files[0];

        if (file && file.type.startsWith('image/')) {
            processImageFile(file);
        }
    }
}

function handlePaste(e) {
    // Only process paste when in image tab
    if (currentContentType !== IMAGE) return;

    const items = (e.clipboardData || e.originalEvent.clipboardData).items;

    for (let i = 0; i < items.length; i++) {
        if (items[i].type.indexOf('image') !== -1) {
            const file = items[i].getAsFile();
            processImageFile(file);
            break;
        }
    }
}

function navigateBack() {
    window.location.replace('/DisplayStorageItem.html?id=' + parentId) ;
}

function undoAction() {
    window.location.replace('/Undo.html?id=' + currentNoteId) ;
}

function renderNote() {
    const noteContents = document.getElementById('note-contents');
    const noteTitle = document.getElementById('note-title');

    const contentItems = noteContents.querySelectorAll('.content-item');
    contentItems.forEach(item => item.remove());

    noteTitle.textContent = title;

    contents.forEach(renderContent);

    updateEmptyNoteMessage();
}

function init() {

    const backBtn = document.getElementById('back-btn');
    const undoBtn = document.getElementById('undo-btn');
    const editNameBtn = document.getElementById('edit-name-btn');
    const noteNameInput = document.getElementById('note-name-input');
    const saveNameBtn = document.getElementById('save-name-btn');
    const tabButtons = document.querySelectorAll('.tab-button');
    const addContentButton = document.getElementById('add-content-button');
    const imageFileInput = document.getElementById('image-file-input');
    const imageUploadButton = document.getElementById('image-upload-button');
    const removePreviewButton = document.getElementById('remove-preview');
    const cancelEditButton = document.getElementById('cancel-edit-button');

    renderNote();

    backBtn.addEventListener('click', navigateBack);
    undoBtn.addEventListener('click', undoAction);

    editNameBtn.addEventListener('click', startEditingNoteName);
    saveNameBtn.addEventListener('click', saveNoteName);
    noteNameInput.addEventListener('keydown', function(e) {
        if (e.key === 'Enter') {
            saveNoteName();
        }
    });

    tabButtons.forEach(button => {
        button.addEventListener('click', () => switchTab(button.dataset.type));
    });

    addContentButton.addEventListener('click', addContent);

    cancelEditButton.addEventListener('click', cancelEditing);

    imageUploadButton.addEventListener('click', () => imageFileInput.click());
    imageFileInput.addEventListener('change', handleFileSelect);
    removePreviewButton.addEventListener('click', clearImagePreview);

    setupDragAndDrop();

    document.addEventListener('paste', handlePaste);
}
document.addEventListener('DOMContentLoaded', init);