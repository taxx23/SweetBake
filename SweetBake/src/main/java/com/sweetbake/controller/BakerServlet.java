package com.sweetbake.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "BakerServlet", urlPatterns = {"/baker"})
public class BakerServlet extends HttpServlet {

    // Simple placeholder order record until Backend teammate's Order model is ready
    public static class BakerOrder {
        private int orderId;
        private String customerName;
        private String categoryName;
        private String status;
        private String rejectionReason;

        public BakerOrder(int orderId, String customerName, String categoryName, String status) {
            this.orderId = orderId;
            this.customerName = customerName;
            this.categoryName = categoryName;
            this.status = status;
        }

        public int getOrderId() {
            return orderId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRejectionReason() {
            return rejectionReason;
        }

        public void setRejectionReason(String rejectionReason) {
            this.rejectionReason = rejectionReason;
        }
    }

    // Temporary sample orders until OrderDAO.getAll() is connected
    private static final List<BakerOrder> sampleOrders = new ArrayList<>();
    private static final List<String> statusHistory = new ArrayList<>();

    @Override
    public void init() {
        if (sampleOrders.isEmpty()) {
            sampleOrders.add(new BakerOrder(1, "Fatima", "Custom Cakes", "PENDING"));
            sampleOrders.add(new BakerOrder(2, "Iman", "Cupcakes", "ACCEPTED"));
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Object user = (session != null) ? session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Map<String, Integer> counts = new HashMap<>();
        counts.put("PENDING", 0);
        counts.put("ACCEPTED", 0);
        counts.put("BAKING", 0);
        counts.put("READY", 0);
        counts.put("PICKED_UP", 0);
        counts.put("REJECTED", 0);
        counts.put("CANCELLED", 0);

        for (BakerOrder order : sampleOrders) {
            String st = order.getStatus();
            if (counts.containsKey(st)) {
                counts.put(st, counts.get(st) + 1);
            }
        }

        request.setAttribute("orders", sampleOrders);
        request.setAttribute("counts", counts);
        request.getRequestDispatcher("/baker.jsp").forward(request, response);
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

        String orderIdParam = request.getParameter("orderId");
        String action = request.getParameter("action");
        String newStatus = request.getParameter("newStatus");
        String reason = request.getParameter("reason");

        if (orderIdParam != null && !orderIdParam.isBlank() && action != null) {
            int orderId = Integer.parseInt(orderIdParam);

            for (BakerOrder order : sampleOrders) {
                if (order.getOrderId() == orderId) {
                    String oldStatus = order.getStatus();

                    if ("accept".equals(action)) {
                        order.setStatus("ACCEPTED");
                        logHistory(orderId, oldStatus, "ACCEPTED", user.toString(), null);
                    } else if ("reject".equals(action)) {
                        order.setStatus("REJECTED");
                        order.setRejectionReason(reason);
                        logHistory(orderId, oldStatus, "REJECTED", user.toString(), reason);
                    } else if ("status".equals(action) && newStatus != null && !newStatus.isBlank()) {
                        String upperStatus = newStatus.trim().toUpperCase();
                        order.setStatus(upperStatus);
                        logHistory(orderId, oldStatus, upperStatus, user.toString(), null);
                    }
                    break;
                }
            }
        }

        response.sendRedirect(request.getContextPath() + "/baker");
    }

    private void logHistory(int orderId, String oldStatus, String newStatus, String changedBy, String reason) {
        // TODO: insert status change into order_status_history table via DAO/PreparedStatement
        String entry = orderId + " | " + oldStatus + " -> " + newStatus + " | by " + changedBy
                + (reason != null && !reason.isBlank() ? " | reason: " + reason : "")
                + " | " + LocalDateTime.now();
        statusHistory.add(entry);
    }
}