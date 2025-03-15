package uk.ac.ucl.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Commands.*;


import java.io.IOException;

@WebServlet("/AddContentToNote.html")

public class AddContentToNote extends HttpServlet
{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        final int id= (Integer)Integer.parseInt(request.getParameter("id"));
        final String type = request.getParameter("type");
        final String value = request.getParameter("value");

        Command command = new AddContentToNoteCommand(id,type,value) ;
        command.run() ;

        String targetJSP = "/DisplayStorageItem.html?id=" + id ;

        response.sendRedirect(targetJSP);
    }
}
