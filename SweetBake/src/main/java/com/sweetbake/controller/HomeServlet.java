package com.sweetbake.controller;

import com.sweetbake.dao.MenuDAO;
import com.sweetbake.dao.ReviewDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {

    private MenuDAO menuDAO = new MenuDAO();
    private ReviewDAO reviewDAO = new ReviewDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("topCategory", menuDAO.getTopCategory());
            request.setAttribute("reviews", reviewDAO.getLatestReviews(3));
            request.setAttribute("categories", menuDAO.getCategories());
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
    }
}
