
package com.sweetbake.util;

import com.sweetbake.dao.ReviewDAO;
import com.sweetbake.model.Review;

public class TestReviewDAO {

    public static void main(String[] args) {
        try {
            Review review = new Review();

            review.setOrderId(1);
            review.setCustomerId(1);
            review.setRating(5);
            review.setComment("Amazing cake! Loved it!");

            ReviewDAO dao = new ReviewDAO();

            boolean created = dao.createReview(review);

            System.out.println("Review created: " + created);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
