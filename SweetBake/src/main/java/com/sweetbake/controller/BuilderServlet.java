package com.sweetbake.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "BuilderServlet", urlPatterns = {"/builder"})
public class BuilderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Temporary sample options until CategoryDAO & OptionDAO are wired up
        List<String> categories = Arrays.asList("Custom Cakes", "Cupcakes", "Cheesecake", "Brownies", "Tarts");
        List<String> sizes = Arrays.asList("Small", "Medium", "Large");
        List<String> flavors = Arrays.asList("Vanilla", "Chocolate", "Red Velvet");

        request.setAttribute("categories", categories);
        request.setAttribute("sizes", sizes);
        request.setAttribute("flavors", flavors);

        request.getRequestDispatcher("/builder.jsp").forward(request, response);
    }
}