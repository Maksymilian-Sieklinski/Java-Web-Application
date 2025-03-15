package uk.ac.ucl.model.Commands;

import uk.ac.ucl.model.StorageItems.*;

public class AddContentToNoteCommand extends Command
{
    final private String type;
    final private String value;

    public AddContentToNoteCommand(final int id, final String type, final String value)
    {
        super(id);
        this.value = value;
        this.type = type;
    }

    public boolean execute()
    {
        Note note = (Note)model.getStorageItem(id) ;
        Content content = model.getContent(type,note, value) ;
        model.addContentToNote(note, content) ;
        result = content;
        return true;
    }

    public void reverse()
    {
        Content content = (Content)result;
        Note note = (Note)model.getStorageItem(id) ;
        result = model.deleteContentInNote(note, content.getId()) ;
    }

}
