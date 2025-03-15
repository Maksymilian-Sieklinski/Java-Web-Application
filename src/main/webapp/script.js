const DIRECTORY = 'directory';
const NOTE = 'note';

let currentModalAction = null;

function showCreateModal(type) {
    const createModal = document.getElementById('create-modal');
    const modalTitle = document.getElementById('modal-title');
    const newItemNameInput = document.getElementById('new-item-name');

    modalTitle.textContent = type === DIRECTORY ? 'Create New Directory' : 'Create New Note';
    newItemNameInput.value = '';
    currentModalAction = type;

    createModal.classList.remove('hidden');
    createModal.style.display = 'flex';

    newItemNameInput.focus();
}

function hideModal() {
    const createModal = document.getElementById('create-modal');
    createModal.classList.add('hidden');
    createModal.style.display = 'none';
    currentModalAction = null;
}

function showMoveModal() {
    const moveModal = document.getElementById('move-modal');
    const currentDirName = document.getElementById('current-dir-name');
    const allItemsContainer = document.getElementById('all-items-container');

    if(allItems.length === 0) window.location.replace('/GetAllStorageItems.html?id=' + currentDirectoryId);

    currentDirName.textContent = currentDirectoryName;

    allItemsContainer.innerHTML = '';

    const loadingIndicator = document.createElement('div');
    loadingIndicator.className = 'loading-indicator';
    loadingIndicator.textContent = 'Loading items...';
    allItemsContainer.appendChild(loadingIndicator);

    moveModal.classList.remove('hidden');
    moveModal.style.display = 'flex';

    displayAllItems(allItems);
}

function displayAllItems(items) {
    const allItemsContainer = document.getElementById('all-items-container');

    allItemsContainer.innerHTML = '';

    if (items.length === 0) {
        const noItemsMessage = document.createElement('div');
        noItemsMessage.textContent = 'No items available to move.';
        noItemsMessage.style.textAlign = 'center';
        noItemsMessage.style.padding = '1rem';
        noItemsMessage.style.color = '#666';
        allItemsContainer.appendChild(noItemsMessage);
        return;
    }

    items.forEach(item => {
        const moveItem = document.createElement('div');
        moveItem.className = 'move-item';

        const itemContent = document.createElement('div');

        const iconClass = item.type === DIRECTORY ? 'fas fa-folder directory-icon' : 'fas fa-file-alt note-icon';
        itemContent.innerHTML = `
            <div class="item-content">
                <i class="${iconClass} item-icon"></i>
                <div class="item-name">${item.name}</div>
            </div>
        `;

        moveItem.appendChild(itemContent);

        moveItem.addEventListener('click', () => {
            window.location.replace('/MoveStorageItem.html?id=' + currentDirectoryId
                + '&movedItemId=' + item.id);
            hideMoveModal();
        });

        allItemsContainer.appendChild(moveItem);
    });
}

function hideMoveModal() {
    const moveModal = document.getElementById('move-modal');
    moveModal.classList.add('hidden');
    moveModal.style.display = 'none';
}

function showSearchModal() {
    const searchModal = document.getElementById('search-modal');
    const searchKeywordInput = document.getElementById('search-keyword');
    const searchResultsContainer = document.getElementById('search-results-container');

    searchResultsContainer.innerHTML = '';

    searchKeywordInput.value = '';

    searchModal.classList.remove('hidden');
    searchModal.style.display = 'flex';

    searchKeywordInput.focus();
}

function hideSearchModal() {
    const searchModal = document.getElementById('search-modal');
    searchModal.classList.add('hidden');
    searchModal.style.display = 'none';
}

function performSearch() {
    const searchResultsContainer = document.getElementById('search-results-container');
    const searchKeywordInput = document.getElementById('search-keyword');
    const keyword = searchKeywordInput.value.trim();

    searchResultsContainer.innerHTML = '';
    const loadingIndicator = document.createElement('div');
    loadingIndicator.className = 'loading-indicator';
    loadingIndicator.textContent = 'Searching...';
    searchResultsContainer.appendChild(loadingIndicator);

    if(!searchResultsReady) {
        window.location.replace('/SearchIndex.html?id=' + currentDirectoryId
            + '&keyword=' + encodeURIComponent(keyword));
    }
}

function displaySearchResults(results) {
    searchResultsReady = false;
    const searchResultsContainer = document.getElementById('search-results-container');

    searchResultsContainer.innerHTML = '';

    if (results.length === 0) {
        const noResultsMessage = document.createElement('div');
        noResultsMessage.textContent = 'No items found matching your search.';
        noResultsMessage.style.textAlign = 'center';
        noResultsMessage.style.padding = '1rem';
        noResultsMessage.style.color = '#666';
        searchResultsContainer.appendChild(noResultsMessage);
        return;
    }

    results.forEach(item => {
        const resultItem = document.createElement('div');
        resultItem.className = 'move-item';

        const itemContent = document.createElement('div');

        const iconClass = item.type === DIRECTORY ? 'fas fa-folder directory-icon' : 'fas fa-file-alt note-icon';
        itemContent.innerHTML = `
            <div class="item-content">
                <i class="${iconClass} item-icon"></i>
                <div class="item-name">${item.name}</div>
            </div>
        `;

        resultItem.appendChild(itemContent);

        resultItem.addEventListener('click', () => {
            navigateTo(item.id);
            hideSearchModal();
        });

        searchResultsContainer.appendChild(resultItem);
    });
}

function renderCurrentDirectory() {
    const directoryNameDisplay = document.getElementById('directory-name-display');
    const directoryNameInput = document.getElementById('directory-name-input');
    const backBtn = document.getElementById('back-btn');
    const itemsContainer = document.getElementById('items-container');
    const emptyMessage = document.getElementById('empty-message');

    directoryNameDisplay.textContent = currentDirectoryName;
    directoryNameInput.value = currentDirectoryName;

    backBtn.disabled = parentDirectoryId === null;

    itemsContainer.innerHTML = '';

    // Show empty message if no items
    if (currentItems.length === 0) {
        emptyMessage.classList.remove('hidden');
    } else {
        emptyMessage.classList.add('hidden');

        currentItems.forEach(item => {
            const itemCard = document.createElement('div');
            itemCard.className = 'item-card';

            const itemContent = document.createElement('div');
            itemContent.className = 'item-content';

            const itemIcon = document.createElement('i');
            if (item.type === DIRECTORY) {
                itemIcon.className = 'fas fa-folder item-icon directory-icon';
            } else {
                itemIcon.className = 'fas fa-file-alt item-icon note-icon';
            }
            itemContent.appendChild(itemIcon);

            const itemName = document.createElement('div');
            itemName.className = 'item-name';

            const nameLink = document.createElement('span');
            nameLink.className = 'clickable-link';
            nameLink.textContent = item.name;

            nameLink.addEventListener('click', () => navigateTo(item.id));

            itemName.appendChild(nameLink);
            itemContent.appendChild(itemName);
            itemCard.appendChild(itemContent);

            const deleteBtn = document.createElement('button');
            deleteBtn.className = 'delete-btn';
            deleteBtn.innerHTML = '<i class="fas fa-times"></i>';
            deleteBtn.addEventListener('click', (e) => {
                e.stopPropagation(); // Prevent event from bubbling up
                deleteItem(item.id);
            });
            itemCard.appendChild(deleteBtn);

            itemsContainer.appendChild(itemCard);
        });
    }
}

function navigateTo(id) {
    window.location.replace('/DisplayStorageItem.html?id=' + id);
}

function navigateBack() {
    window.location.replace('/DisplayStorageItem.html?id=' + parentDirectoryId);
}

function undoAction() {
    window.location.replace('/Undo.html?id=' + currentDirectoryId);
}

function createNewItem() {
    const newItemNameInput = document.getElementById('new-item-name');
    const name = newItemNameInput.value.trim();

    if (!name) {
        alert('Please enter a name');
        return;
    }

    if (currentModalAction === DIRECTORY) {
        createDirectory(name);
    } else if (currentModalAction === NOTE) {
        createNote(name);
    }
    hideModal();
}

function sendAddingForm(url, name)
{
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = url; 

    const parentIdInput = document.createElement('input');
    parentIdInput.type = 'hidden';
    parentIdInput.name = 'id';
    parentIdInput.value = currentDirectoryId;

    const nameInput = document.createElement('input');
    nameInput.type = 'hidden';
    nameInput.name = 'name';
    nameInput.value = name;

    form.appendChild(parentIdInput);
    form.appendChild(nameInput);

    document.body.appendChild(form);
    form.submit();
}

function createDirectory(name) {
    sendAddingForm('NameFiltered/AddIndex.html', name) ;

}

function createNote(name) {
    sendAddingForm('NameFiltered/AddNote.html', name) ;
}

function deleteItem(id) {
    window.location.replace('/DeleteStorageItem.html?id=' + id +
        '&parentId=' + currentDirectoryId);
}

function startEditingDirectoryName() {
    const directoryNameDisplay = document.getElementById('directory-name-display');
    const directoryNameEdit = document.getElementById('directory-name-edit');
    const directoryNameInput = document.getElementById('directory-name-input');

    directoryNameDisplay.classList.add('hidden');
    directoryNameEdit.classList.remove('hidden');
    directoryNameInput.focus();
    directoryNameInput.select();
}

function saveDirectoryName() {
    const directoryNameDisplay = document.getElementById('directory-name-display');
    const directoryNameEdit = document.getElementById('directory-name-edit');
    const directoryNameInput = document.getElementById('directory-name-input');

    const newName = directoryNameInput.value.trim();
    if (!newName) {
        directoryNameInput.value = currentDirectoryName;
        directoryNameDisplay.classList.remove('hidden');
        directoryNameEdit.classList.add('hidden');
        return;
    }
    
    // Create a form to submit the POST request
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = 'NameFiltered/ChangeNameStorageItem.html';

    const itemIdInput = document.createElement('input');
    itemIdInput.type = 'hidden';
    itemIdInput.name = 'id';
    itemIdInput.value = currentDirectoryId;

    const newNameInput = document.createElement('input');
    newNameInput.type = 'hidden';
    newNameInput.name = 'name';
    newNameInput.value = newName;

    form.appendChild(itemIdInput);
    form.appendChild(newNameInput);

    document.body.appendChild(form);
    form.submit();
}

function init() {
    const createModal = document.getElementById('create-modal');
    const moveModal = document.getElementById('move-modal');
    const searchModal = document.getElementById('search-modal');

    createModal.classList.add('hidden');
    createModal.style.display = 'none';
    moveModal.classList.add('hidden');
    moveModal.style.display = 'none';
    searchModal.classList.add('hidden');
    searchModal.style.display = 'none';

    const backBtn = document.getElementById('back-btn');
    const undoBtn = document.getElementById('undo-btn');
    const searchBtn = document.getElementById('search-btn');
    const addDirectoryBtn = document.getElementById('add-directory-btn');
    const addNoteBtn = document.getElementById('add-note-btn');
    const moveToDirectoryBtn = document.getElementById('move-to-dir-btn');
    const editNameBtn = document.getElementById('edit-name-btn');
    const saveNameBtn = document.getElementById('save-name-btn');
    const closeModalBtn = document.getElementById('close-modal-btn');
    const closeMoveModalBtn = document.getElementById('close-move-modal-btn');
    const closeSearchModalBtn = document.getElementById('close-search-modal-btn');
    const createItemBtn = document.getElementById('create-item-btn');
    const searchSubmitBtn = document.getElementById('search-submit-btn');
    const directoryNameInput = document.getElementById('directory-name-input');
    const newItemNameInput = document.getElementById('new-item-name');
    const searchKeywordInput = document.getElementById('search-keyword');

    backBtn.addEventListener('click', navigateBack);
    undoBtn.addEventListener('click', undoAction);
    searchBtn.addEventListener('click', showSearchModal);

    addDirectoryBtn.addEventListener('click', () => showCreateModal(DIRECTORY));
    addNoteBtn.addEventListener('click', () => showCreateModal(NOTE));
    moveToDirectoryBtn.addEventListener('click', showMoveModal);

    editNameBtn.addEventListener('click', startEditingDirectoryName);
    saveNameBtn.addEventListener('click', saveDirectoryName);

    closeModalBtn.addEventListener('click', (e) => {
        e.stopPropagation();
        hideModal();
    });

    closeMoveModalBtn.addEventListener('click', (e) => {
        e.stopPropagation();
        hideMoveModal();
    });

    closeSearchModalBtn.addEventListener('click', (e) => {
        e.stopPropagation();
        hideSearchModal();
    });

    createItemBtn.addEventListener('click', (e) => {
        e.stopPropagation();
        createNewItem();
    });

    searchSubmitBtn.addEventListener('click', (e) => {
        e.stopPropagation();
        performSearch();
    });

    // Handle Enter key in the name input
    directoryNameInput.addEventListener('keydown', (e) => {
        if (e.key === 'Enter') {
            saveDirectoryName();
        }
    });

    // Handle Enter key in the new item name input
    newItemNameInput.addEventListener('keydown', (e) => {
        if (e.key === 'Enter') {
            createNewItem();
        }
    });

    // Handle Enter key in the search keyword input
    searchKeywordInput.addEventListener('keydown', (e) => {
        if (e.key === 'Enter') {
            performSearch();
        }
    });

    // Close modals when clicking outside
    createModal.addEventListener('click', (e) => {
        if (e.target === createModal) {
            hideModal();
        }
    });

    moveModal.addEventListener('click', (e) => {
        if (e.target === moveModal) {
            hideMoveModal();
        }
    });

    searchModal.addEventListener('click', (e) => {
        if (e.target === searchModal) {
            hideSearchModal();
        }
    });

    // Prevent clicks inside modal content from closing the modal
    document.querySelector('.modal-content').addEventListener('click', (e) => {
        e.stopPropagation();
    });

    document.querySelector('.move-modal-content').addEventListener('click', (e) => {
        e.stopPropagation();
    });

    document.querySelectorAll('.modal-content').forEach(content => {
        content.addEventListener('click', (e) => {
            e.stopPropagation();
        });
    });

    renderCurrentDirectory();
    if(displayAllItemsAtBeginning) showMoveModal() ;
    if(searchResultsReady)
    {
        showSearchModal() ;
        displaySearchResults(searchResults) ;
    }
}
document.addEventListener('DOMContentLoaded', init);