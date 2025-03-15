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

@WebServlet("/DeleteContent.html")

public class DeleteContent extends HttpServlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        final int id= (Integer)Integer.parseInt(request.getParameter("id"));
        final int contentId = (Integer)Integer.parseInt(request.getParameter("contentId"));

        Command command = new DeleteContentCommand(id,contentId) ;
        command.run() ;

        String targetJSP = "/DisplayStorageItem.html?id=" + id ;

        response.sendRedirect(targetJSP);
    }
}
