package com.sweetbake.dao;

import com.sweetbake.model.Order;
import com.sweetbake.model.OrderItem;
import com.sweetbake.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public void addOrder(Order order) throws SQLException {
        String orderSql = "INSERT INTO orders (customer_id, pickup_date, notes, total_price) VALUES (?, ?, ?, ?)";
        String itemSql = "INSERT INTO order_items (order_id, category_id, quantity, price) VALUES (?, ?, ?, ?)";
        String optionSql = "INSERT INTO order_item_options (item_id, option_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection()) {

            PreparedStatement orderStmt = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
            orderStmt.setInt(1, order.getCustomerId());
            orderStmt.setString(2, order.getPickupDate());
            orderStmt.setString(3, order.getNotes());
            orderStmt.setDouble(4, order.getTotalPrice());
            orderStmt.executeUpdate();

            ResultSet orderKeys = orderStmt.getGeneratedKeys();
            orderKeys.next();
            int orderId = orderKeys.getInt(1);

            for (OrderItem item : order.getItems()) {
                PreparedStatement itemStmt = conn.prepareStatement(itemSql, Statement.RETURN_GENERATED_KEYS);
                itemStmt.setInt(1, orderId);
                itemStmt.setInt(2, item.getCategoryId());
                itemStmt.setInt(3, item.getQuantity());
                itemStmt.setDouble(4, item.getPrice());
                itemStmt.executeUpdate();

                ResultSet itemKeys = itemStmt.getGeneratedKeys();
                itemKeys.next();
                int itemId = itemKeys.getInt(1);

                for (int optionId : item.getOptionIds()) {
                    PreparedStatement optionStmt = conn.prepareStatement(optionSql);
                    optionStmt.setInt(1, itemId);
                    optionStmt.setInt(2, optionId);
                    optionStmt.executeUpdate();
                }
            }
        }
    }

    public List<Order> getOrdersByCustomer(int customerId) throws SQLException {
        String sql = "SELECT o.*, u.full_name, r.review_id FROM orders o "
                + "JOIN users u ON o.customer_id = u.user_id "
                + "LEFT JOIN reviews r ON o.order_id = r.order_id "
                + "WHERE o.customer_id = ? ORDER BY o.order_id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            return readOrders(conn, stmt.executeQuery());
        }
    }

    public List<Order> getAllOrders() throws SQLException {
        String sql = "SELECT o.*, u.full_name, r.review_id FROM orders o "
                + "JOIN users u ON o.customer_id = u.user_id "
                + "LEFT JOIN reviews r ON o.order_id = r.order_id "
                + "ORDER BY o.order_id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            return readOrders(conn, stmt.executeQuery());
        }
    }

    public void updateStatus(int orderId, String status) throws SQLException {
        String sql = "UPDATE orders SET status = ? WHERE order_id = ? AND status <> 'CANCELLED'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            stmt.executeUpdate();
        }
    }

    public void cancelOrder(int orderId, int customerId) throws SQLException {
        String sql = "UPDATE orders SET status = 'CANCELLED' "
                + "WHERE order_id = ? AND customer_id = ? AND status = 'PENDING'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            stmt.setInt(2, customerId);
            stmt.executeUpdate();
        }
    }

    private List<Order> readOrders(Connection conn, ResultSet rs) throws SQLException {
        List<Order> orders = new ArrayList<>();

        while (rs.next()) {
            Order order = new Order();
            order.setOrderId(rs.getInt("order_id"));
            order.setCustomerId(rs.getInt("customer_id"));
            order.setCustomerName(rs.getString("full_name"));
            order.setPickupDate(rs.getString("pickup_date"));
            order.setNotes(rs.getString("notes"));
            order.setTotalPrice(rs.getDouble("total_price"));
            order.setStatus(rs.getString("status"));
            order.setReviewed(rs.getObject("review_id") != null);
            orders.add(order);
        }

        for (Order order : orders) {
            order.setItems(getItems(conn, order.getOrderId()));
        }
        return orders;
    }

    private List<OrderItem> getItems(Connection conn, int orderId) throws SQLException {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT i.item_id, i.category_id, c.name, i.quantity, i.price, "
                + "GROUP_CONCAT(op.name ORDER BY op.option_id SEPARATOR ', ') AS details "
                + "FROM order_items i "
                + "JOIN categories c ON i.category_id = c.category_id "
                + "LEFT JOIN order_item_options io ON i.item_id = io.item_id "
                + "LEFT JOIN options op ON io.option_id = op.option_id "
                + "WHERE i.order_id = ? "
                + "GROUP BY i.item_id, i.category_id, c.name, i.quantity, i.price";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, orderId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            OrderItem item = new OrderItem();
            item.setItemId(rs.getInt("item_id"));
            item.setCategoryId(rs.getInt("category_id"));
            item.setCategoryName(rs.getString("name"));
            item.setQuantity(rs.getInt("quantity"));
            item.setPrice(rs.getDouble("price"));
            item.setDetails(rs.getString("details"));
            items.add(item);
        }
        return items;
    }
}
