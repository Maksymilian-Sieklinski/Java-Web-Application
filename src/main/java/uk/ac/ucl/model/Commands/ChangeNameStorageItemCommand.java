package uk.ac.ucl.model.Commands;

import uk.ac.ucl.model.*;
import uk.ac.ucl.model.StorageItems.StorageItem;

public class ChangeNameStorageItemCommand extends Command
{
    final private String newName;

    public ChangeNameStorageItemCommand(final int id, final String newName)
    {
        super(id) ;
        this.newName = newName;
    }

    public boolean execute()
    {
        StorageItem item = model.getStorageItem(id) ;
        result = model.changeNameOfStorageItem(item, newName) ;
        return true;
    }

    public void reverse()
    {
        StorageItem item = model.getStorageItem(id) ;
        model.changeNameOfStorageItem(item,(String)result) ;
    }
}
