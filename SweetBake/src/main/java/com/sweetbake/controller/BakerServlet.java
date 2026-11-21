package com.sweetbake.controller;

import com.sweetbake.dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "BakerServlet", urlPatterns = {"/baker"})
public class BakerServlet extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("orders", orderDAO.getAllOrders());
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        request.getRequestDispatcher("/WEB-INF/views/baker.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String status = request.getParameter("status");
        String[] allowed = {"ACCEPTED", "REJECTED", "BAKING", "READY", "PICKED_UP"};

        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            for (String s : allowed) {
                if (s.equals(status)) {
                    orderDAO.updateStatus(orderId, status);
                }
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("baker");
            return;
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        response.sendRedirect("baker");
    }
}
