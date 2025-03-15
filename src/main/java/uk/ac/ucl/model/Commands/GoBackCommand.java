package uk.ac.ucl.model.Commands;

import uk.ac.ucl.model.*;
import uk.ac.ucl.model.StorageItems.StorageItem;

public class GoBackCommand extends Command
{
    public GoBackCommand(final int id)
    {
        super(id);
    }

    public boolean execute()
    {
        StorageItem item = model.getStorageItem(id);
        result = model.getParentId(item);
        return false;
    }

    public void reverse()
    {
        return ;
    }
}
