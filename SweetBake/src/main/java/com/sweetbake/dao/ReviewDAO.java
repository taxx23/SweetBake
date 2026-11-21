
package com.sweetbake.dao;

import com.sweetbake.model.Review;
import com.sweetbake.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReviewDAO {

    public boolean createReview(Review review) throws SQLException {

        if (review.getRating() < 1 || review.getRating() > 5) {
            throw new IllegalArgumentException(
                    "Rating must be between 1 and 5.");
        }

        String sql =
                "INSERT INTO reviews " +
                "(order_id, customer_id, rating, comment) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, review.getOrderId());
            stmt.setInt(2, review.getCustomerId());
            stmt.setInt(3, review.getRating());
            stmt.setString(4, review.getComment());

            return stmt.executeUpdate() == 1;
        }
    }
}
