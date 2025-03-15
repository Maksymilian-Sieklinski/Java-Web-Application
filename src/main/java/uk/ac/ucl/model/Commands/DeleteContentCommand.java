package uk.ac.ucl.model.Commands;

import uk.ac.ucl.model.StorageItems.*;

public class DeleteContentCommand extends Command
{
    final private int contentId;

    public DeleteContentCommand(final int id, int contentId)
    {
        super(id) ;
        this.contentId = contentId;
    }

    public boolean execute()
    {
        Note note = (Note)model.getStorageItem(id) ;
        result = model.deleteContentInNote(note, contentId) ;
        return true;
    }

    public void reverse()
    {
        Note note = (Note)model.getStorageItem(id) ;
        model.addContentToNote(note,(Content)result) ;
    }
}
