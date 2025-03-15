package uk.ac.ucl.model.Commands;

public class GetAllStorageItemsCommand extends Command
{
    public GetAllStorageItemsCommand(final int id)
    {
        super(id) ;
    }

    public boolean execute()
    {
        result = model.getAllStorageItems() ;
        return false;
    }

    public void reverse()
    {
        return ;
    }
}
