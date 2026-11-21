package com.sweetbake.dao;

import com.sweetbake.model.Category;
import com.sweetbake.model.Option;
import com.sweetbake.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {

    public List<Category> getCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories ORDER BY category_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                categories.add(readCategory(rs));
            }
        }
        return categories;
    }

    public Category getCategory(int categoryId) throws SQLException {
        String sql = "SELECT * FROM categories WHERE category_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return readCategory(rs);
            }
        }
        return null;
    }

    public Category getTopCategory() throws SQLException {
        String sql = "SELECT c.category_id, c.name, c.description, c.image, SUM(i.quantity) AS total "
                + "FROM categories c "
                + "JOIN order_items i ON c.category_id = i.category_id "
                + "JOIN orders o ON i.order_id = o.order_id "
                + "WHERE o.status NOT IN ('CANCELLED', 'REJECTED') "
                + "GROUP BY c.category_id, c.name, c.description, c.image "
                + "ORDER BY total DESC LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return readCategory(rs);
            }
        }
        return null;
    }

    public void updateCategory(Category category) throws SQLException {
        String sql = "UPDATE categories SET name = ?, description = ?, image = ? WHERE category_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            stmt.setString(3, category.getImage());
            stmt.setInt(4, category.getCategoryId());
            stmt.executeUpdate();
        }
    }

    public List<Option> getOptions(int categoryId) throws SQLException {
        List<Option> options = new ArrayList<>();
        String sql = "SELECT o.*, c.name AS category_name FROM options o "
                + "JOIN categories c ON o.category_id = c.category_id "
                + "WHERE o.category_id = ? AND o.is_active = TRUE ORDER BY o.option_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                options.add(readOption(rs));
            }
        }
        return options;
    }

    public List<Option> getAllOptions() throws SQLException {
        List<Option> options = new ArrayList<>();
        String sql = "SELECT o.*, c.name AS category_name FROM options o "
                + "JOIN categories c ON o.category_id = c.category_id "
                + "WHERE o.is_active = TRUE ORDER BY o.category_id, o.option_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                options.add(readOption(rs));
            }
        }
        return options;
    }

    public Option getOption(int optionId) throws SQLException {
        String sql = "SELECT o.*, c.name AS category_name FROM options o "
                + "JOIN categories c ON o.category_id = c.category_id "
                + "WHERE o.option_id = ? AND o.is_active = TRUE";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, optionId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return readOption(rs);
            }
        }
        return null;
    }

    public void addOption(Option option) throws SQLException {
        String sql = "INSERT INTO options (category_id, option_type, name, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, option.getCategoryId());
            stmt.setString(2, option.getOptionType());
            stmt.setString(3, option.getName());
            stmt.setDouble(4, option.getPrice());
            stmt.executeUpdate();
        }
    }

    public void updateOption(int optionId, String name, double price) throws SQLException {
        String sql = "UPDATE options SET name = ?, price = ? WHERE option_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, optionId);
            stmt.executeUpdate();
        }
    }

    public void removeOption(int optionId) throws SQLException {
        String sql = "UPDATE options SET is_active = FALSE WHERE option_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, optionId);
            stmt.executeUpdate();
        }
    }

    private Category readCategory(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setCategoryId(rs.getInt("category_id"));
        category.setName(rs.getString("name"));
        category.setDescription(rs.getString("description"));
        category.setImage(rs.getString("image"));
        return category;
    }

    private Option readOption(ResultSet rs) throws SQLException {
        Option option = new Option();
        option.setOptionId(rs.getInt("option_id"));
        option.setCategoryId(rs.getInt("category_id"));
        option.setCategoryName(rs.getString("category_name"));
        option.setOptionType(rs.getString("option_type"));
        option.setName(rs.getString("name"));
        option.setPrice(rs.getDouble("price"));
        return option;
    }
}
