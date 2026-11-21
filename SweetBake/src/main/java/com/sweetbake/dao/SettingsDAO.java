
package com.sweetbake.dao;

import com.sweetbake.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingsDAO {

    public String getSetting(String key) throws SQLException {

        String sql =
                "SELECT setting_value FROM settings " +
                "WHERE setting_key = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, key);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("setting_value");
                }
            }
        }

        return null;
    }

    public int getDailyCapacity() throws SQLException {

        String value = getSetting("daily_capacity");

        if (value == null) {
            throw new SQLException("Daily capacity setting not found.");
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new SQLException("Invalid daily capacity setting.", e);
        }
    }
}
