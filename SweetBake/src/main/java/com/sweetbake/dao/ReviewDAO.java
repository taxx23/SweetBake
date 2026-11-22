package com.sweetbake.dao;

import com.sweetbake.model.Review;
import com.sweetbake.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    public void addReview(Review review) throws SQLException {
        String sql = "INSERT INTO reviews (order_id, rating, comment) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, review.getOrderId());
            stmt.setInt(2, review.getRating());
            stmt.setString(3, review.getComment());
            stmt.executeUpdate();
        }
    }

    public boolean canReview(int orderId, int customerId) throws SQLException {
        String sql = "SELECT o.order_id FROM orders o "
                + "LEFT JOIN reviews r ON o.order_id = r.order_id "
                + "WHERE o.order_id = ? AND o.customer_id = ? AND o.status = 'PICKED_UP' AND r.review_id IS NULL";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            stmt.setInt(2, customerId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public List<Review> getLatestReviews(int count) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT r.*, u.full_name FROM reviews r "
                + "JOIN orders o ON r.order_id = o.order_id "
                + "JOIN users u ON o.customer_id = u.user_id "
                + "ORDER BY r.review_id DESC LIMIT ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, count);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                reviews.add(readReview(rs));
            }
        }
        return reviews;
    }

    public List<Review> getAllReviews() throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT r.*, u.full_name FROM reviews r "
                + "JOIN orders o ON r.order_id = o.order_id "
                + "JOIN users u ON o.customer_id = u.user_id "
                + "ORDER BY r.review_id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                reviews.add(readReview(rs));
            }
        }
        return reviews;
    }

    public void deleteReview(int reviewId) throws SQLException {
        String sql = "DELETE FROM reviews WHERE review_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reviewId);
            stmt.executeUpdate();
        }
    }

    private Review readReview(ResultSet rs) throws SQLException {
        Review review = new Review();
        review.setReviewId(rs.getInt("review_id"));
        review.setOrderId(rs.getInt("order_id"));
        review.setCustomerName(rs.getString("full_name"));
        review.setRating(rs.getInt("rating"));
        review.setComment(rs.getString("comment"));
        return review;
    }
}
