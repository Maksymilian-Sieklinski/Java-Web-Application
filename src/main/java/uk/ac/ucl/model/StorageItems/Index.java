package uk.ac.ucl.model.StorageItems;

import java.util.*;

public class Index extends StorageItem
{
    private ArrayList<StorageItem>subItems;

    public Index(Index Parent, String name)
    {
        super(Parent, name);
        subItems = new ArrayList<>();
    }

    public ArrayList<StorageItem> getSubItems()
    {
        return new ArrayList<>(subItems);
    }

    @Override
    public ArrayList<StorageItem> getAllStorageItems()
    {
        ArrayList<StorageItem> allStorageItems = new ArrayList<>();
        allStorageItems.add(this) ;
        for(StorageItem item : subItems)
        {
            allStorageItems.addAll(item.getAllStorageItems());
        }
        return allStorageItems;
    }

    public Note addNote(String name)
    {
        Note note = new Note(this, name);
        Names.put(name,note.getId()) ;
        subItems.add(note);
        return note;
    }

    public Index addIndex(String name)
    {
        Index index = new Index(this, name);
        Names.put(name,index.getId()) ;
        subItems.add(index);
        return index;
    }

    public void addStorageItem(StorageItem item)
    {
        subItems.add(item);
        item.setParent(this);
        Names.put(item.getName(),item.getId()) ;
    }

    public void deleteStorageItem(StorageItem item)
    {
        subItems.remove(item);
        Names.remove(item.getName());
    }
}
