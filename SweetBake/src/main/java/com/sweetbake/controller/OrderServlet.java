package com.sweetbake.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "OrderServlet", urlPatterns = {"/order"})
public class OrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Object user = (session != null) ? session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String pickupDate = request.getParameter("pickupDate");
        String notes = request.getParameter("notes");
        String orderItems = request.getParameter("orderItems");

        if (pickupDate == null || pickupDate.isBlank() || orderItems == null || orderItems.isBlank()) {
            request.setAttribute("error", "Please select at least one dessert and a pickup date.");
            request.getRequestDispatcher("/builder.jsp").forward(request, response);
            return;
        }

        LocalDate date = LocalDate.parse(pickupDate);
        if (date.isBefore(LocalDate.now().plusDays(2))) {
            request.setAttribute("error", "Pickup date must be at least 2 days ahead.");
            request.getRequestDispatcher("/builder.jsp").forward(request, response);
            return;
        }

        // TODO: check daily capacity & recalculate price with DAO/JDBC
        // TODO: insert order into DB (MySQL auto-generates order_id) and get generated orderId
        // TODO: save each dessert item using that generated orderId
        String savedNotes = notes;

        response.sendRedirect(request.getContextPath() + "/my-orders");
    }
}