package com.sweetbake.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // TODO: load featured desserts/categories via CategoryDAO / OptionDAO
        List<String> featuredDesserts = new ArrayList<>();
        featuredDesserts.add("Custom Cakes");
        featuredDesserts.add("Cupcakes");

        // TODO: load top ordered dessert via OrderDAO
        String topOrderedDessert = "Custom Cakes";

        // TODO: load latest reviews via ReviewDAO
        List<String> recentReviews = new ArrayList<>();
        recentReviews.add("5/5 - Amazing custom cake!");

        request.setAttribute("featuredDesserts", featuredDesserts);
        request.setAttribute("topOrderedDessert", topOrderedDessert);
        request.setAttribute("recentReviews", recentReviews);

        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }
}