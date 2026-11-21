
package com.sweetbake.dao;

import com.sweetbake.model.Order;
import com.sweetbake.model.OrderOption;
import com.sweetbake.util.DBConnection;
import java.sql.*;
import java.util.List;
import java.sql.ResultSet;

public class OrderDAO {

    public int createOrder(Order order, List<OrderOption> selectedOptions)
            throws SQLException {

        String orderSQL =
        "INSERT INTO orders " +
        "(customer_id, category_id, pickup_date, message, notes, total_price, status) " +
        "VALUES (?, ?, ?, ?, ?, ?, 'PENDING')";

        String optionSQL =
        "INSERT INTO order_options " +
        "(order_id, option_id, price_at_order) " +
        "VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try {
                int orderId;

                try (PreparedStatement stmt = conn.prepareStatement(
                        orderSQL, Statement.RETURN_GENERATED_KEYS)) {

                    stmt.setInt(1, order.getCustomerId());
                    stmt.setInt(2, order.getCategoryId());
                    stmt.setDate(3, java.sql.Date.valueOf(order.getPickupDate()));
                    stmt.setString(4, order.getMessage());
                    stmt.setString(5, order.getNotes());
                    stmt.setBigDecimal(6, order.getTotalPrice());

                    stmt.executeUpdate();

                    try (ResultSet rs = stmt.getGeneratedKeys()) {
                        if (!rs.next()) {
                            throw new SQLException("Order ID was not generated.");
                        }
                        orderId = rs.getInt(1);
                    }
                }

                if (selectedOptions != null) {
                    try (PreparedStatement stmt =
                            conn.prepareStatement(optionSQL)) {

                        for (OrderOption option : selectedOptions) {
                            stmt.setInt(1, orderId);
                            stmt.setInt(2, option.getOptionId());
                            stmt.setBigDecimal(3, option.getPriceAtOrder());
                            stmt.addBatch();
                        }

                        stmt.executeBatch();
                    }
                }

                conn.commit();
                return orderId;

            } catch (SQLException | RuntimeException e) {
                conn.rollback();
                throw e;
            }
        }
    }
    
    
public boolean updateOrderStatus(
        int orderId, String newStatus, int changedBy,
        String rejectionReason) throws SQLException {

    String[] allowedStatuses = {
        "PENDING", "ACCEPTED", "REJECTED",
        "BAKING", "READY", "PICKED_UP", "CANCELLED"
    };

    if (newStatus == null ||
            !java.util.Arrays.asList(allowedStatuses).contains(newStatus)) {
        throw new IllegalArgumentException("Invalid order status.");
    }

    String selectSQL =
            "SELECT status FROM orders WHERE order_id = ? FOR UPDATE";

    String updateSQL =
            "UPDATE orders SET status = ?, rejection_reason = ? " +
            "WHERE order_id = ?";

    String historySQL =
            "INSERT INTO order_status_history " +
            "(order_id, changed_by, old_status, new_status) " +
            "VALUES (?, ?, ?, ?)";

    try (Connection conn = DBConnection.getConnection()) {
        conn.setAutoCommit(false);

        try {
            String oldStatus;

            try (PreparedStatement stmt =
                    conn.prepareStatement(selectSQL)) {

                stmt.setInt(1, orderId);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        conn.rollback();
                        return false;
                    }

                    oldStatus = rs.getString("status");
                }
            }

            if (oldStatus.equals(newStatus)) {
                conn.rollback();
                return false;
            }

            try (PreparedStatement stmt =
                    conn.prepareStatement(updateSQL)) {

                stmt.setString(1, newStatus);
                stmt.setString(2,
                        "REJECTED".equals(newStatus)
                                ? rejectionReason : null);
                stmt.setInt(3, orderId);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt =
                    conn.prepareStatement(historySQL)) {

                stmt.setInt(1, orderId);
                stmt.setInt(2, changedBy);
                stmt.setString(3, oldStatus);
                stmt.setString(4, newStatus);
                stmt.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException | RuntimeException e) {
            conn.rollback();
            throw e;
        }
    }
}

    
}
