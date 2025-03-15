package uk.ac.ucl.model.Commands;

import uk.ac.ucl.model.*;

public class CheckIfNameTakenCommand extends Command
{
    final private String Name;

    public CheckIfNameTakenCommand(final int id,String Name)
    {
        super(id) ;
        this.Name = Name;
    }

    public boolean execute()
    {
        result = model.checkIfNameIsTaken(Name) ;

        return false;
    }
    
    public void reverse()
    {
        return ;
    }
}
