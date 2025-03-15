package uk.ac.ucl.model.Commands;

public class CheckIfCanMoveCommand extends Command
{
    final int movedItemId;

    public CheckIfCanMoveCommand(final int id, final int movedItemId)
    {
        super(id) ;
        this.movedItemId = movedItemId;
    }

    public boolean execute()
    {
        if(id == movedItemId)
        {
            result = false;
            return false;
        }
        Command command = new GoBackCommand(id) ;
        command.execute() ;
        while((int)command.getResult() != 0 && (int)command.getResult() != movedItemId)
        {
            command = new GoBackCommand((int)command.getResult()) ;
            command.execute() ;
        }
        result = (movedItemId == (int)command.getResult());
        return false;
    }

    public void reverse()
    {
        return ;
    }
}
