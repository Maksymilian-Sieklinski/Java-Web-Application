package uk.ac.ucl.model.Commands;

import uk.ac.ucl.model.*;
import uk.ac.ucl.model.StorageItems.Index;
import uk.ac.ucl.model.StorageItems.StorageItem;

public class AddNoteCommand extends Command
{
    final String name;

    public AddNoteCommand(final int id, final String name)
    {
        super(id);
        this.name = name;
    }


    public boolean execute()
    {
        Index index = (Index)model.getStorageItem(id) ;
        result = model.addNoteToIndex(index, name) ;
        return true;
    }

    public void reverse()
    {
        Index index = (Index)model.getStorageItem(id) ;
        StorageItem item = model.getStorageItem((int)result) ;
        model.deleteStorageItem(index,item) ;
        goToParentAfterUndo = true;
    }
}
