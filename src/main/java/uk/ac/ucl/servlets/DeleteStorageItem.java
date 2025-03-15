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
import uk.ac.ucl.model.StorageItems.Note;


import java.io.IOException;

@WebServlet("/DeleteStorageItem.html")

public class DeleteStorageItem extends HttpServlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        final int id = (Integer)Integer.parseInt(request.getParameter("id"));
        final int parentId = (Integer)Integer.parseInt(request.getParameter("parentId"));

        Command command = new DeleteStorageItemCommand(id, parentId) ;
        command.run() ;

        String targetJSP = "/DisplayStorageItem.html?id=" + parentId ;

        response.sendRedirect(targetJSP);
    }
}
