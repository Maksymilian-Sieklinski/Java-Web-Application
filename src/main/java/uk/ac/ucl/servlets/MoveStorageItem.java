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
import uk.ac.ucl.model.StorageItems.StorageItem;


import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/MoveStorageItem.html")

public class MoveStorageItem extends HttpServlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        final int id = Integer.parseInt(request.getParameter("id"));
        final int movedItemId = Integer.parseInt(request.getParameter("movedItemId"));


        Command command = new MoveStorageItemCommand(id, movedItemId);
        command.run() ;

        String targetJSP = "/DisplayStorageItem.html?id=" + id ;

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher(targetJSP);
        dispatch.forward(request, response);
    }
}
