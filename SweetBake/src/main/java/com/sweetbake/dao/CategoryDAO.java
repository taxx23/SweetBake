
package com.sweetbake.dao;

import com.sweetbake.model.Category;
import com.sweetbake.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    public List<Category> getAllCategories() throws SQLException {

        List<Category> categories = new ArrayList<>();

        String sql = "SELECT * FROM categories WHERE is_active = 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Category category = new Category();

                category.setCategoryId(rs.getInt("category_id"));
                category.setName(rs.getString("name"));
                category.setDescription(rs.getString("description"));
                category.setImage(rs.getString("image"));
                category.setActive(rs.getBoolean("is_active"));

                categories.add(category);
            }
        }

        return categories;
    }
}
