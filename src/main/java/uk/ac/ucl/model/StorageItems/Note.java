package uk.ac.ucl.model.StorageItems;

import java.util.*;

public class Note extends StorageItem
{
    private ArrayList<Content>contents;

    public Note(Index Parent, String name)
    {
        super(Parent, name);
        contents = new ArrayList<>();
    }

    public ArrayList<Content> getContents()
    {
        ArrayList<Content>temp = new ArrayList<>(contents) ;
        Collections.sort(temp);
        return temp;
    }

    public void addContent(Content content)
    {
        contents.add(content);
    }

    public Content getSpecificContent(final int id)
    {
        for(Content content : contents)
        {
            if(content.getId() == id) return content;
        }
        return null;
    }

    public Content deleteContent(final int id)
    {
        Content content = getSpecificContent(id);
        contents.remove(content);
        return content;
    }

    public boolean searchContents(String keyword)
    {
        for(Content content : contents)
        {
            if(content.search(keyword)) return true;
        }
        return false;
    }
}
