
package com.sweetbake.dao;

import com.sweetbake.model.Option;
import com.sweetbake.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OptionDAO {

    public List<Option> getOptionsByCategory(int categoryId)
            throws SQLException {

        List<Option> options = new ArrayList<>();

        String sql = "SELECT * FROM options "
                   + "WHERE category_id = ? AND is_active = 1 "
                   + "ORDER BY option_type, option_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    Option option = new Option();

                    option.setOptionId(rs.getInt("option_id"));
                    option.setCategoryId(rs.getInt("category_id"));
                    option.setOptionType(rs.getString("option_type"));
                    option.setName(rs.getString("name"));
                    option.setPrice(rs.getBigDecimal("price"));
                    option.setActive(rs.getBoolean("is_active"));

                    options.add(option);
                }
            }
        }

        return options;
    }
}
