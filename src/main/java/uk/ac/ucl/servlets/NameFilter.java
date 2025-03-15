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

@WebFilter("/NameFiltered/*")

public class NameFilter extends HttpFilter
{
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        final int id = Integer.parseInt(request.getParameter("id"));
        final String name = request.getParameter("name");

        Command command = new CheckIfNameTakenCommand(id,name);
        command.run() ;


        if((boolean)command.getResult())
        {
            request.getSession().setAttribute("Error", true);
            request.getSession().setAttribute("ErrorMessage",
                    "This name is already taken");
            response.sendRedirect("/DisplayStorageItem.html?id="+id);
            return ;
        }
        chain.doFilter(request, response);
    }
}
