package com.sweetbake.controller;

import com.sweetbake.dao.MenuDAO;
import com.sweetbake.dao.OrderDAO;
import com.sweetbake.model.Category;
import com.sweetbake.model.Option;
import com.sweetbake.model.Order;
import com.sweetbake.model.OrderItem;
import com.sweetbake.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {

    private MenuDAO menuDAO = new MenuDAO();
    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        showCart(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                addItem(request, response);
            } else if ("remove".equals(action)) {
                int index = Integer.parseInt(request.getParameter("index"));
                List<OrderItem> cart = getCart(request.getSession());
                if (index >= 0 && index < cart.size()) {
                    cart.remove(index);
                }
                response.sendRedirect("cart");
            } else if ("checkout".equals(action)) {
                checkout(request, response);
            } else {
                response.sendRedirect("cart");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("cart");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void addItem(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        Category category = menuDAO.getCategory(categoryId);

        if (category == null || quantity < 1 || quantity > 20) {
            response.sendRedirect("home");
            return;
        }

        List<String> chosenIds = new ArrayList<>();
        String[] types = {"SIZE", "FLAVOR", "FILLING", "FROSTING", "SAUCE"};
        for (String type : types) {
            if (request.getParameter(type) != null) {
                chosenIds.add(request.getParameter(type));
            }
        }
        if (request.getParameterValues("TOPPING") != null) {
            for (String id : request.getParameterValues("TOPPING")) {
                chosenIds.add(id);
            }
        }

        OrderItem item = new OrderItem();
        item.setCategoryId(categoryId);
        item.setCategoryName(category.getName());
        item.setQuantity(quantity);

        double price = 0;
        String details = "";
        boolean hasSize = false;

        for (String id : chosenIds) {
            Option option = menuDAO.getOption(Integer.parseInt(id));
            if (option != null && option.getCategoryId() == categoryId) {
                price = price + option.getPrice();
                item.getOptionIds().add(option.getOptionId());
                if (details.isEmpty()) {
                    details = option.getName();
                } else {
                    details = details + ", " + option.getName();
                }
                if (option.getOptionType().equals("SIZE")) {
                    hasSize = true;
                }
            }
        }

        if (!hasSize) {
            response.sendRedirect("builder?category=" + categoryId);
            return;
        }

        item.setPrice(price);
        item.setDetails(details);
        getCart(request.getSession()).add(item);
        response.sendRedirect("cart");
    }

    private void checkout(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<OrderItem> cart = getCart(session);
        String pickupDate = request.getParameter("pickupDate");
        String notes = request.getParameter("notes");

        if (cart.isEmpty()) {
            request.setAttribute("error", "Your cart is empty.");
            showCart(request, response);
            return;
        }

        LocalDate date;
        try {
            date = LocalDate.parse(pickupDate);
        } catch (Exception e) {
            date = null;
        }
        if (date == null || date.isBefore(LocalDate.now().plusDays(2))) {
            request.setAttribute("error", "Please choose a pickup date at least 2 days from today.");
            showCart(request, response);
            return;
        }

        double total = 0;
        for (OrderItem item : cart) {
            total = total + item.getTotal();
        }

        Order order = new Order();
        order.setCustomerId(user.getUserId());
        order.setPickupDate(pickupDate);
        order.setNotes(notes);
        order.setTotalPrice(total);
        order.setItems(cart);
        orderDAO.addOrder(order);

        session.removeAttribute("cart");
        response.sendRedirect("my-orders");
    }

    private void showCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<OrderItem> cart = getCart(request.getSession());
        double total = 0;
        for (OrderItem item : cart) {
            total = total + item.getTotal();
        }
        request.setAttribute("cartTotal", total);
        request.setAttribute("minDate", LocalDate.now().plusDays(2).toString());
        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
    }

    private List<OrderItem> getCart(HttpSession session) {
        List<OrderItem> cart = (List<OrderItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }
}
