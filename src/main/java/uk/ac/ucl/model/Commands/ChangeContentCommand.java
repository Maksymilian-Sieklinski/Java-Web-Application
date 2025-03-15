package uk.ac.ucl.model.Commands;

import uk.ac.ucl.model.StorageItems.*;

public class ChangeContentCommand extends Command
{
    final private int contentId;
    final private String newValue;

    public ChangeContentCommand(int contentId, int id, String newValue)
    {
        super(id) ;
        this.contentId = contentId;
        this.newValue = newValue;
    }

    public boolean execute()
    {
        Note note = (Note)model.getStorageItem(id) ;
        result = model.editContentInNote(note, contentId, newValue) ;
        return true;
    }

    public void reverse()
    {
        Note note = (Note)model.getStorageItem(id) ;
        model.editContentInNote(note, contentId, (String)result) ;
    }
}
