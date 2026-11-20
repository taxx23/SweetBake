package com.sweetbake.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            request.setAttribute("error", "Please enter both email and password.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        // TODO: look up user by email via UserDAO and verify password using BCrypt
        // Temporary placeholder check until UserDAO & BCrypt are wired up
        String role = null;
        if ("admin@sweetbake.com".equalsIgnoreCase(email.trim()) && "admin123".equals(password)) {
            role = "ADMIN";
        } else if ("baker@sweetbake.com".equalsIgnoreCase(email.trim()) && "baker123".equals(password)) {
            role = "BAKER";
        } else if ("customer@sweetbake.com".equalsIgnoreCase(email.trim()) && "cust123".equals(password)) {
            role = "CUSTOMER";
        }

        if (role == null) {
            request.setAttribute("error", "Invalid email or password.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", email.trim());
        session.setAttribute("role", role);

        if ("ADMIN".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/admin");
        } else if ("BAKER".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/baker");
        } else {
            response.sendRedirect(request.getContextPath() + "/menu");
        }
    }
}