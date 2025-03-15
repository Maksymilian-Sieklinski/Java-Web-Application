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

@WebServlet("/DisplayStorageItem.html")

public class DisplayStorageItem extends HttpServlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        final int id= (Integer)Integer.parseInt(request.getParameter("id"));

        Command command = new GetStorageItemCommand(id) ;
        command.run() ;

        request.setAttribute("Value", command.getResult());
        String targetJSP = "" ;

        if(command.getResult() instanceof Note) targetJSP = "/NoteJSP.jsp";
        else targetJSP = "/IndexJSP.jsp" ;

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher(targetJSP);
        dispatch.forward(request, response);
    }
}
