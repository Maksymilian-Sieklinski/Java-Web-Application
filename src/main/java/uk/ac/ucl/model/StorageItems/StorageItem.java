package uk.ac.ucl.model.StorageItems;

import uk.ac.ucl.model.*;

import java.util.*;

public abstract class StorageItem
{
    public static int numberOfItems = 0 ;
    public static HashMap<String,Integer>Names = new HashMap<String,Integer>(); /// maps names of StorageItems to Ids
    final private int id;
    private String name;
    private Index Parent;

    protected StorageItem(Index Parent, String name)
    {
        id = numberOfItems++;
        this.name = name;
        this.Parent = Parent;
        Names.put(name, id);
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        Names.remove(this.name);
        this.name = name;
        Names.put(name, id);
    }

    public Index getParent()
    {
        return Parent;
    }

    public void setParent(Index Parent)
    {
        this.Parent = Parent;
    }

    public ArrayList<StorageItem> getAllStorageItems()
    {
        return new ArrayList<>(Arrays.asList(this));
    }

    public boolean searchName(String keyword)
    {
        return name.toLowerCase().contains(keyword);
    }
}
