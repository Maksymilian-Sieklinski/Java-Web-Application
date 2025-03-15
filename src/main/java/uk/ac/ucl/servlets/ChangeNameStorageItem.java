package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Commands.*;

import java.io.IOException;

@WebServlet("/NameFiltered/ChangeNameStorageItem.html")

public class ChangeNameStorageItem extends HttpServlet
{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        final int id= (Integer)Integer.parseInt(request.getParameter("id"));
        final String newName = request.getParameter("name") ;

        Command command = new ChangeNameStorageItemCommand(id,newName) ;
        command.run() ;

        String targetJSP = "/DisplayStorageItem.html?id=" + id ;

        response.sendRedirect(targetJSP);
    }
}
