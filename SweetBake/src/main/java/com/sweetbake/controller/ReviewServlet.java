package com.sweetbake.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ReviewServlet", urlPatterns = {"/review"})
public class ReviewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Object user = (session != null) ? session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String orderId = request.getParameter("orderId");

        // TODO: check via OrderDAO / ReviewDAO that order belongs to this user,
        // status is PICKED_UP, and no review exists yet for this orderId
        request.setAttribute("orderId", orderId);
        request.getRequestDispatcher("/review.jsp").forward(request, response);
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

        String orderId = request.getParameter("orderId");
        String rating = request.getParameter("rating");
        String comment = request.getParameter("comment");

        if (orderId == null || orderId.isBlank() || rating == null || rating.isBlank()) {
            request.setAttribute("error", "Please select a rating from 1 to 5.");
            request.setAttribute("orderId", orderId);
            request.getRequestDispatcher("/review.jsp").forward(request, response);
            return;
        }

        int stars = Integer.parseInt(rating);
        if (stars < 1 || stars > 5) {
            request.setAttribute("error", "Rating must be between 1 and 5.");
            request.setAttribute("orderId", orderId);
            request.getRequestDispatcher("/review.jsp").forward(request, response);
            return;
        }

        // TODO: save review (orderId, stars, comment, customer) via ReviewDAO
        String savedComment = comment;

        response.sendRedirect(request.getContextPath() + "/my-orders");
    }
}