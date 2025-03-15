package uk.ac.ucl.model.Commands;

import org.json.simple.*;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;
import uk.ac.ucl.model.StorageItems.Index;

public abstract class Command
{
    protected Model model;
    protected Object result;
    protected boolean goToParentAfterUndo;
    final protected int id; /// iD of Storage Item from which command is called

    Command(final int id)
    {
        this.id = id;
        model = ModelFactory.getModel();
        goToParentAfterUndo = false;
    }

    public void run()
    {
        model.executeCommand(this);
    }

    public abstract boolean execute() ;

    public Object getResult()
    {
        return result;
    }

    public boolean getGoToParentAfterUndo()
    {
        return goToParentAfterUndo;
    }

    public abstract void reverse() ;
}
