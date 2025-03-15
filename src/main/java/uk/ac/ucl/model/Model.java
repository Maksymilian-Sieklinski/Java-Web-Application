package uk.ac.ucl.model;

import uk.ac.ucl.model.Commands.Command;
import uk.ac.ucl.model.Commands.UndoCommand;
import uk.ac.ucl.model.StorageItems.*;

import java.util.*;

public class Model
{
    private Index overLord ;
    private CommandHistory commandHistory ;

    public Model(Index overLord)
    {
        this.overLord = overLord ;
        commandHistory = new CommandHistory() ;
    }

    public void executeCommand(Command command)
    {
        if(command.execute())
        {
            commandHistory.push(command);
            ModelFactory.saveToFile() ;
        }
        if(command instanceof UndoCommand) ModelFactory.saveToFile() ;
    }

    public ArrayList<StorageItem> getAllStorageItems()
    {
        return overLord.getAllStorageItems() ;
    }

    public StorageItem getStorageItem(final int id)
    {
        for(StorageItem item : getAllStorageItems())
        {
            if(item.getId() == id) return item ;
        }
        return null;
    }

    public boolean checkIfNameIsTaken(final String name)
    {
        return StorageItem.Names.containsKey(name) ;
    }

    public Content getContent(String targetType, Note parentNote, String value)
    {
        return new Content(ModelFactory.getEnumFromString(targetType), parentNote, value) ;
    }

    public int addIndexToIndex(Index index, String name)
    {
        return index.addIndex(name).getId() ;
    }

    public int addNoteToIndex(Index index, String name)
    {
        return index.addNote(name).getId() ;
    }

    public void addContentToNote(Note note, Content content)
    {
        note.addContent(content);
    }

    public String changeNameOfStorageItem(StorageItem item, String newName)
    {
        String temp = item.getName() ;
        item.setName(newName) ;
        return temp ;
    }

    public int getParentId(StorageItem item)
    {
        return item.getParent().getId() ;
    }

    public String editContentInNote(Note note, final int contentId, final String newValue)
    {
        Content content = note.getSpecificContent(contentId) ;
        String temp = content.getValue() ;
        content.editValue(newValue) ;
        return temp;
    }

    public Content deleteContentInNote(Note note, final int contentId)
    {
        return note.deleteContent(contentId) ;
    }

    public void deleteStorageItem(Index index, StorageItem item)
    {
        index.deleteStorageItem(item) ;
    }

    public void addStorageItem(Index index, StorageItem item)
    {
        index.addStorageItem(item) ;
    }

    public Index getMainIndex()
    {
        return overLord;
    }

    public void setMainIndex(Index mainIndex)
    {
        overLord = mainIndex ;
    }

    public boolean undo(final int idOfStorageItemFromWhichUndoIsCalled)
    {
        Command command = commandHistory.pop() ;
        if(command == null) return false;
        command.reverse();
        return command.getGoToParentAfterUndo() &&
                idOfStorageItemFromWhichUndoIsCalled == (int)command.getResult();
    }

    public ArrayList<StorageItem> searchIndex(Index index, String keyword)
    {
        ArrayList<StorageItem>allItems = index.getAllStorageItems() ;
        ArrayList<StorageItem>searchResult = new ArrayList<>() ;
        for(StorageItem item : allItems)
        {
            if(item.searchName(keyword))
            {
                searchResult.add(item) ;
                continue;
            }
            if(item instanceof Note && ((Note)item).searchContents(keyword))
            {
                searchResult.add(item) ;
            }
        }
        return searchResult ;
    }
}
