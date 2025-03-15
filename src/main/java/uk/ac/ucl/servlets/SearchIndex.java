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

@WebServlet("/SearchIndex.html")

public class SearchIndex extends HttpServlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        final int id= (Integer)Integer.parseInt(request.getParameter("id"));
        final String keyword = (String)request.getParameter("keyword");

        Command command = new SearchCommand(id, keyword) ;
        command.run() ;

        request.setAttribute("searchResult", command.getResult());
        request.setAttribute("searchResultsReady", true) ;
        String targetJSP = "/DisplayStorageItem.html?id=" + id ;

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher(targetJSP);
        dispatch.forward(request, response);
    }
}
