package com.sweetbake.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AdminServlet", urlPatterns = {"/admin"})
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Object user = (session != null) ? session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // TODO: load options, stats, reviews, bakers, and daily capacity from DAOs
        List<String> menuItems = new ArrayList<>();
        menuItems.add("Custom Cakes");
        menuItems.add("Cupcakes");

        request.setAttribute("menuItems", menuItems);
        request.setAttribute("dailyCapacity", 10);
        request.getRequestDispatcher("/admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Object user = (session != null) ? session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");

        if ("addOption".equals(action)) {
            String name = request.getParameter("name");
            String price = request.getParameter("price");
            // TODO: add menu option via OptionDAO
            String itemInfo = name + " - " + price;
        } else if ("editOption".equals(action)) {
            String optionId = request.getParameter("optionId");
            String price = request.getParameter("price");
            // TODO: update menu option via OptionDAO
            String updateInfo = optionId + " -> " + price;
        } else if ("hideOption".equals(action)) {
            String optionId = request.getParameter("optionId");
            // TODO: hide menu option via OptionDAO
            String hiddenId = optionId;
        } else if ("addBaker".equals(action)) {
            String bakerName = request.getParameter("bakerName");
            String bakerEmail = request.getParameter("bakerEmail");
            String bakerPassword = request.getParameter("bakerPassword");
            // TODO: hash password and add baker user via UserDAO
            String bakerInfo = bakerName + " (" + bakerEmail + ")";
        } else if ("setCapacity".equals(action)) {
            String dailyCapacity = request.getParameter("dailyCapacity");
            // TODO: update daily capacity via SettingsDAO
            String cap = dailyCapacity;
        }

        response.sendRedirect(request.getContextPath() + "/admin");
    }
}