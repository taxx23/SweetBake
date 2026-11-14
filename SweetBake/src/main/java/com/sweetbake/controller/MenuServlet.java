package com.sweetbake.controller;

import com.sweetbake.dao.CategoryDAO;
import com.sweetbake.model.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Call the DAO to get categories from the database
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> categories = categoryDAO.getActiveCategories();

        // 2. Attach the list to the request object
        request.setAttribute("categories", categories);

        // 3. Forward the request to menu.jsp
        request.getRequestDispatcher("/views/menu.jsp").forward(request, response);
    }
}