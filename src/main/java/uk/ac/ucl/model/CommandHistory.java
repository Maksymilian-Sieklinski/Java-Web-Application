package uk.ac.ucl.model;

import uk.ac.ucl.model.Commands.*;

import java.util.*;

public class CommandHistory
{
    Stack<Command>history;

    public CommandHistory()
    {
        history = new Stack<>();
    }

    public void push(Command command)
    {
        history.add(command);
    }

    public Command pop()
    {
        if(history.isEmpty()) return null;
        return history.pop();
    }

}
