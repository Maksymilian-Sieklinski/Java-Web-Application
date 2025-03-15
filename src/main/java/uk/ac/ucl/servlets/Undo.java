package uk.ac.ucl.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Commands.*;
import uk.ac.ucl.model.*;
import uk.ac.ucl.model.StorageItems.*;


import java.io.IOException;
import java.util.List;

@WebServlet("/Undo.html")

public class Undo extends HttpServlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        final int id= (Integer)Integer.parseInt(request.getParameter("id"));

        Command command1 = new GoBackCommand(id) ;
        command1.run() ;
        Command command2 = new UndoCommand(id) ;
        command2.run() ;

        String targetJSP = "/DisplayStorageItem.html?id=";
        if((boolean)command2.getResult()) targetJSP += command1.getResult() ;
        else targetJSP += id ;

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher(targetJSP);
        dispatch.forward(request, response);
    }
}
