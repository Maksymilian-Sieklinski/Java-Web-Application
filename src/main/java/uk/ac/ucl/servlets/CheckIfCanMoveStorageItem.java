package uk.ac.ucl.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Commands.*;

import uk.ac.ucl.model.*;


import java.io.IOException;
import java.util.List;

@WebFilter("/MoveStorageItem.html")

public class CheckIfCanMoveStorageItem extends HttpFilter
{
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        final int id = Integer.parseInt(request.getParameter("id"));
        final int movedItemId = Integer.parseInt(request.getParameter("movedItemId"));

        Command command = new CheckIfCanMoveCommand(id,movedItemId);
        command.run() ;

        if((boolean)command.getResult())
        {
            request.getSession().setAttribute("Error", true);
            request.getSession().setAttribute("ErrorMessage",
                    "Index you are trying to move, contains the index you are moving it to");
            response.sendRedirect("/DisplayStorageItem.html?id=" + id);
            return ;
        }
        chain.doFilter(request, response);
    }
}
