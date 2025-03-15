package uk.ac.ucl.model.Commands;

public class UndoCommand extends Command
{
    public UndoCommand(final int id){super(id);}

    public boolean execute()
    {
        result = model.undo(id) ;
        return false;
    }

    public void reverse()
    {
        return ;
    }
}
