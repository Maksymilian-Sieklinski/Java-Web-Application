package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Commands.*;


import java.io.IOException;

@WebServlet("/ChangeContent.html")

public class ChangeContent extends HttpServlet
{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        final int id= (Integer)Integer.parseInt(request.getParameter("id"));
        final int parentId = (Integer)Integer.parseInt(request.getParameter("parentId"));
        final String newValue = request.getParameter("newValue");

        Command command = new ChangeContentCommand(id,parentId, newValue) ;
        command.run() ;

        String targetJSP = "/DisplayStorageItem.html?id=" + parentId ;

        response.sendRedirect(targetJSP);
    }
}
