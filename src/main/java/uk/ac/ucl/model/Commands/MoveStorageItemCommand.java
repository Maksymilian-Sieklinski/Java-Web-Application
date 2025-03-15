package uk.ac.ucl.model.Commands;

import uk.ac.ucl.model.StorageItems.*;

public class MoveStorageItemCommand extends Command
{
    final int movedItemId;

    public MoveStorageItemCommand(final int id, final int movedItemId)
    {
        super(id);
        this.movedItemId = movedItemId;
    }

    public boolean execute()
    {
        StorageItem item = model.getStorageItem(movedItemId) ;
        Index parentIndexOfItem = (Index)model.getStorageItem(item.getParent().getId()) ;
        model.deleteStorageItem(parentIndexOfItem,item);

        Index index = (Index)model.getStorageItem(id) ;
        model.addStorageItem(index,item) ;
        result = parentIndexOfItem ;
        return true;
    }

    public void reverse()
    {
        StorageItem item = model.getStorageItem(movedItemId) ;
        Index parentIndexOfItem = (Index)model.getStorageItem(id) ;
        model.deleteStorageItem(parentIndexOfItem,item);

        Index index = (Index)result;
        model.addStorageItem(index,item) ;
    }
}
