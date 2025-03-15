package uk.ac.ucl.model.Commands;

import uk.ac.ucl.model.*;
import uk.ac.ucl.model.StorageItems.Index;
import uk.ac.ucl.model.StorageItems.StorageItem;

public class AddIndexCommand extends Command
{
    final String name ;

    public AddIndexCommand(final int id, final String name)
    {
        super(id);
        this.name = name;
    }

    public boolean execute()
    {
        Index index = (Index)model.getStorageItem(id) ;
        result = model.addIndexToIndex(index, name) ;
        return true;
    }

    public void reverse()
    {
        Index index = (Index)model.getStorageItem(id) ;
        StorageItem item = model.getStorageItem((int)result) ;
        model.deleteStorageItem(index, item) ;
        goToParentAfterUndo = true;
    }
}
