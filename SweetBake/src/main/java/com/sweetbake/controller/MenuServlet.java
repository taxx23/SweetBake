package com.sweetbake.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "MenuServlet", urlPatterns = {"/menu"})
public class MenuServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //temporary hardcoded list of dessert categories until a DAO pulls real records from MySQL
        List<String> Desserts = Arrays.asList(
                "Custom Cakes",
                "Cupcakes",
                "Cheesecake",
                "Brownies",
                "Tarts"
        );

        request.setAttribute("categories", Desserts);
        request.getRequestDispatcher("menu.jsp").forward(request, response);
    }
}