package uk.ac.ucl.model.Commands;

import uk.ac.ucl.model.StorageItems.*;

public class DeleteStorageItemCommand extends Command
{
    final private int parentId;

    public DeleteStorageItemCommand(final int id, int parentId)
    {
        super(id) ;
        this.parentId = parentId;
    }

    public boolean execute()
    {
        Index index = (Index)model.getStorageItem(parentId) ;
        StorageItem item = model.getStorageItem(id) ;
        model.deleteStorageItem(index,item) ;
        result = item ;
        return true;
    }

    public void reverse()
    {
        Index index = (Index)model.getStorageItem(parentId) ;
        StorageItem item = (StorageItem)result;
        model.addStorageItem(index,item) ;
    }
}
