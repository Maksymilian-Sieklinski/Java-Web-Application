<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Note Editor</title>
    <link rel="stylesheet" href="styles2.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>

<%@ page import="uk.ac.ucl.model.StorageItems.Note" %>
<%@ page import="uk.ac.ucl.model.StorageItems.Content" %>
<%@ page import="java.util.ArrayList" %>
<%
    Note note = (Note)request.getAttribute("Value") ;
    int id = note.getId();
    int parentId = note.getParent().getId() ;
    String name = note.getName() ;
    ArrayList<Content> Contents = (ArrayList<Content>)note.getContents() ;
    if(Contents == null) Contents = new ArrayList<Content>() ;

    Boolean error = (Boolean)request.getSession().getAttribute("Error") ;
    String errorMessage = (String)request.getSession().getAttribute("ErrorMessage") ;
    if(error == null) error = false;
%>

<script>

    <%
    if(error)
    {
        request.getSession().setAttribute("Error",false) ;
    %>
    alert('<%=errorMessage%>') ;
    <%}%>

    let currentNoteId = '<%=id%>' ;
    let parentId = '<%=parentId%>' ;
    let title = '<%=name%>' ;
    let contents = [
        <%
            for (Content content : Contents)
            {
        %>
        {
            id: '<%= content.getId() %>',
            type: '<%= content.getType() %>',
            content: `<%=content.getValue()%>`
        }
        <% if (!content.equals(Contents.getLast())){ %>, <% } %>
        <% } %>
    ];
</script>


<body>
<div class="container">
    <div class="header">
        <div class="navigation-buttons">
            <button id="back-btn" class="btn">
                <i class="fas fa-arrow-left"></i>
                Back
            </button>
        </div>

        <div class="note-name-container">
            <div id="note-name-display" class="note-name">
                <span id="note-title"><%=name%></span>
                <button id="edit-name-btn" class="btn-icon">
                    <i class="fas fa-edit"></i>
                </button>
            </div>
            <div id="note-name-edit" class="note-name-edit hidden">
                <input type="text" id="note-name-input" value="Untitled Note">
                <button id="save-name-btn" class="btn-icon">
                    <i class="fas fa-check"></i>
                </button>
            </div>
        </div>

        <button id="undo-btn" class="btn">
            <i class="fas fa-undo"></i>
            Undo
        </button>
    </div>

    <div id="note-contents" class="note-contents">
        <!-- Note contents will be dynamically added here -->
        <div id="empty-note-message" class="empty-message">
            This note is empty. Add some content below.
        </div>
    </div>

    <div class="add-content-section">
        <div class="content-tabs">
            <div class="tab-list">
                <button class="tab-button active" data-type="text">
                    <i class="fas fa-font"></i> Text
                </button>
                <button class="tab-button" data-type="url">
                    <i class="fas fa-link"></i> URL
                </button>
                <button class="tab-button" data-type="image">
                    <i class="fas fa-image"></i> Image
                </button>
            </div>

            <div class="tab-content">
                <div id="text-tab" class="tab-pane active">
                    <textarea id="text-input" placeholder="Enter your text here..."></textarea>
                </div>

                <div id="url-tab" class="tab-pane">
                    <input type="url" id="url-input" placeholder="https://example.com">
                </div>

                <div id="image-tab" class="tab-pane">
                    <div id="image-drop-area" class="image-drop-area">
                        <p>Drag & drop image here, paste from clipboard, or</p>
                        <div class="file-upload-container">
                            <input type="file" id="image-file-input" accept="image/*" class="hidden">
                            <button id="image-upload-button" class="btn">Choose File</button>
                        </div>
                        <p>OR</p>
                        <input type="url" id="image-url-input" placeholder="https://example.com/image.jpg">
                    </div>
                    <div id="image-preview" class="image-preview hidden">
                        <img id="preview-image" src="" alt="Preview">
                        <button id="remove-preview" class="btn-icon delete-btn">
                            <i class="fas fa-times"></i>
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <div class="action-buttons">
            <button id="add-content-button" class="btn">
                <i class="fas fa-plus"></i> Add <span id="content-type-label">Text</span>
            </button>
            <!-- Cancel button for editing -->
            <button id="cancel-edit-button" class="btn" style="display: none;">
                <i class="fas fa-times"></i> Cancel
            </button>
        </div>
    </div>
</div>

<!-- Template for content items -->
<template id="content-item-template">
    <div class="item-card content-item">
        <button class="delete-btn">
            <i class="fas fa-times"></i>
        </button>
        <div class="content-body"></div>
    </div>
</template>

<script src="script2.js"></script>
</body>
</html>