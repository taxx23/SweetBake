package com.sweetbake.controller;

import com.sweetbake.dao.OrderDAO;
import com.sweetbake.dao.ReviewDAO;
import com.sweetbake.model.Review;
import com.sweetbake.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "MyOrdersServlet", urlPatterns = {"/my-orders"})
public class MyOrdersServlet extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();
    private ReviewDAO reviewDAO = new ReviewDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        try {
            request.setAttribute("orders", orderDAO.getOrdersByCustomer(user.getUserId()));
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        request.getRequestDispatcher("/WEB-INF/views/my-orders.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        String action = request.getParameter("action");

        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));

            if ("cancel".equals(action)) {
                orderDAO.cancelOrder(orderId, user.getUserId());
            } else if ("review".equals(action)) {
                int rating = Integer.parseInt(request.getParameter("rating"));
                if (rating >= 1 && rating <= 5 && reviewDAO.canReview(orderId, user.getUserId())) {
                    Review review = new Review();
                    review.setOrderId(orderId);
                    review.setRating(rating);
                    review.setComment(request.getParameter("comment"));
                    reviewDAO.addReview(review);
                }
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("my-orders");
            return;
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        response.sendRedirect("my-orders");
    }
}
