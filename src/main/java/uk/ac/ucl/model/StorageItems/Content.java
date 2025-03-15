package uk.ac.ucl.model.StorageItems;

public class Content implements Comparable<Content>
{
    public static int numberOfContents = 0 ;
    final private int id;
    private String content;
    final private Note parentNote;
    final private TypeContent typeContent;

    public Content(TypeContent typeContent, Note parentNote)
    {
        this.id = numberOfContents++ ;
        this.content = "";
        this.typeContent = typeContent;
        this.parentNote = parentNote;
    }

    public Content(TypeContent typeContent, Note parentNote, String content)
    {
        this.id = numberOfContents++ ;
        this.content = content;
        this.typeContent = typeContent;
        this.parentNote = parentNote;
    }

    public int compareTo(Content other)
    {
        return this.id - other.getId();
    }

    public int getId()
    {
        return id;
    }

    public String getValue()
    {
        return content;
    }

    public void editValue(String newContent)
    {
        content = newContent;
    }

    public TypeContent getType()
    {
        return typeContent;
    }

    public Note getParent()
    {
        return parentNote;
    }

    public boolean search(String keyword)
    {
        if(typeContent != TypeContent.text) return false;
        return content.toLowerCase().contains(keyword);
    }
}
