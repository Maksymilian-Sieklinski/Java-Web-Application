package uk.ac.ucl.model.Commands;

import uk.ac.ucl.model.StorageItems.*;

public class SearchCommand extends Command
{
    final String keyword;

    public SearchCommand(final int id, final String keyword)
    {
        super(id);
        this.keyword = keyword.toLowerCase();
    }

    public boolean execute()
    {
        Index index = (Index)model.getStorageItem(id) ;
        result = model.searchIndex(index, keyword) ;
        return false;
    }

    public void reverse()
    {
        return ;
    }
}
