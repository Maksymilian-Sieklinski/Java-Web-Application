<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Category</title>
    <link rel="stylesheet" href="styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>

<%@ page import="uk.ac.ucl.model.StorageItems.*" %>
<%@ page import="java.util.ArrayList" %>

<%
    Index index = (Index)request.getAttribute("Value") ;
    String name = index.getName() ;
    int parentId = index.getParent().getId() ;
    int id = index.getId() ;
    ArrayList<StorageItem> subItems = index.getSubItems() ;

    Boolean error = (Boolean)request.getSession().getAttribute("Error") ;
    String errorMessage = (String)request.getSession().getAttribute("ErrorMessage") ;
    if(error == null) error = false;

    Boolean displayAllItemsList = (Boolean)request.getAttribute("displayAllItemsList") ;
    if(displayAllItemsList == null) displayAllItemsList = false;

    ArrayList<StorageItem> allItems =
                (ArrayList<StorageItem>) request.getAttribute("allItems");
    if(allItems == null) allItems = new ArrayList<>() ;

    Boolean searchResultsReady = (Boolean)request.getAttribute("searchResultsReady");
    if(searchResultsReady == null) searchResultsReady = false;

    ArrayList<StorageItem>searchResult =
                (ArrayList<StorageItem>)request.getAttribute("searchResult") ;
    if(searchResult == null) searchResult = new ArrayList<>() ;
%>

<script type="text/javascript">

    <%
    if(error)
    {
        request.getSession().setAttribute("Error",false) ;
    %>
    alert('<%=errorMessage%>') ;
    <%}%>

    let currentDirectoryName = '<%=name%>' ;
    let currentDirectoryId = '<%=id%>';
    let parentDirectoryId = '<%=parentId%>';
    let displayAllItemsAtBeginning = <%=displayAllItemsList%> ;
    let currentItems = [
        <%
            for (StorageItem item : subItems) {
        %>
        {
            id: '<%= item.getId() %>',
            name: '<%= item.getName() %>',
            type: '<%=item instanceof Index ? "directory" : "note"%>'
        }
        <% if (!item.equals(subItems.getLast())){ %>, <% } %>
        <% } %>
    ];
    let allItems = [
        <%
            for (StorageItem item : allItems) {
        %>
        {
            id: '<%= item.getId() %>',
            name: '<%= item.getName() %>',
            type: '<%=item instanceof Index ? "directory" : "note"%>'
        }
        <% if (!item.equals(allItems.getLast())){ %>, <% } %>
        <% } %>
    ];

    let searchResultsReady = <%=searchResultsReady%> ;
    let searchResults = [
        <%
            for (StorageItem item : searchResult) {
        %>
        {
            id: '<%= item.getId() %>',
            name: '<%= item.getName() %>',
            type: '<%=item instanceof Index ? "directory" : "note"%>'
        }
        <% if (!item.equals(searchResult.getLast())){ %>, <% } %>
        <% } %>
    ];
</script>


<body>
<div class="container">
    <div class="header">
        <div class="directory-name-container">
            <div id="directory-name-display" class="directory-name"><%=name%>></div>
            <div id="directory-name-edit" class="directory-name-edit hidden">
                <input type="text" id="directory-name-input" value="<%=name%>">
                <button id="save-name-btn" class="btn-icon"><i class="fas fa-save"></i></button>
            </div>
            <button id="edit-name-btn" class="btn-icon"><i class="fas fa-edit"></i></button>
            <button id="search-btn" class="btn-icon"><i class="fas fa-search"></i></button>
        </div>
        <div class="navigation-buttons">
            <button id="back-btn" class="btn" disabled><i class="fas fa-arrow-left"></i> Back</button>
            <button id="undo-btn" class="btn"><i class="fas fa-undo"></i> Undo</button>
        </div>
    </div>

    <div id="items-container" class="items-container">
        <!-- Items will be dynamically added here -->
    </div>

    <div id="empty-message" class="empty-message hidden">
        This directory is empty. Add a directory or note to get started.
    </div>

    <div class="action-buttons">
        <button id="add-directory-btn" class="btn"><i class="fas fa-folder-plus"></i> Add Category</button>
        <button id="add-note-btn" class="btn"><i class="fas fa-file-medical"></i> Add Note</button>
        <button id="move-to-dir-btn" class="btn"><i class="fas fa-exchange-alt"></i> Move to this Category</button>
    </div>
</div>

<!-- Modal for creating new items -->
<div id="create-modal" class="modal hidden" style="display: none;">
    <div class="modal-content">
        <div class="modal-header">
            <h2 id="modal-title">Create New Item</h2>
            <button id="close-modal-btn" class="btn-icon"><i class="fas fa-times"></i></button>
        </div>
        <div class="modal-body">
            <label for="new-item-name">Name:</label>
            <input type="text" id="new-item-name" placeholder="Enter name">
        </div>
        <div class="modal-footer">
            <button id="create-item-btn" class="btn">Create</button>
        </div>
    </div>
</div>

<!-- Modal for moving items -->
<div id="move-modal" class="modal hidden" style="display: none;">
    <div class="modal-content move-modal-content">
        <div class="modal-header">
            <h2>Select an item to move to the current directory: <span id="current-dir-name"></span></h2>
            <button id="close-move-modal-btn" class="btn-icon"><i class="fas fa-times"></i></button>
        </div>
        <div class="modal-body">
            <div id="all-items-container" class="all-items-container">
                <!-- All items will be dynamically added here -->
            </div>
        </div>
    </div>
</div>

<!-- Modal for searching items -->
<div id="search-modal" class="modal hidden" style="display: none;">
    <div class="modal-content search-modal-content">
        <div class="modal-header">
            <h2>Search Items</h2>
            <button id="close-search-modal-btn" class="btn-icon"><i class="fas fa-times"></i></button>
        </div>
        <div class="modal-body">
            <label for="search-keyword">Search:</label>
            <input type="text" id="search-keyword" placeholder="Enter search keyword">
            <div class="modal-footer">
                <button id="search-submit-btn" class="btn">Search</button>
            </div>
            <div id="search-results-container" class="all-items-container">
                <!-- Search results will be displayed here -->
            </div>
        </div>
    </div>
</div>

<script src="script.js"></script>
</body>
</html>