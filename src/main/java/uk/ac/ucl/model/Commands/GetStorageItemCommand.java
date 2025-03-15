package uk.ac.ucl.model.Commands;

import uk.ac.ucl.model.*;

import java.util.ArrayList;

public class GetStorageItemCommand extends Command
{
    public GetStorageItemCommand(final int id)
    {
        super(id) ;
    }

    public boolean execute()
    {
        result = model.getStorageItem(id);
        return false;
    }

    public void reverse()
    {
        return ;
    }
}
