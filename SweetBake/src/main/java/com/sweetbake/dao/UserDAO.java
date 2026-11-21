
package com.sweetbake.dao;

import com.sweetbake.model.User;
import com.sweetbake.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public User findByEmail(String email) throws SQLException {

        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    User user = new User();

                    user.setUserId(rs.getInt("user_id"));
                    user.setFullName(rs.getString("full_name"));
                    user.setEmail(rs.getString("email"));
                    user.setPasswordHash(rs.getString("password_hash"));
                    user.setPhone(rs.getString("phone"));
                    user.setRole(rs.getString("role"));

                    if (rs.getTimestamp("created_at") != null) {
                        user.setCreatedAt(
                            rs.getTimestamp("created_at").toLocalDateTime()
                        );
                    }

                    return user;
                }
            }
        }

        return null;
    }
    
    
public boolean createUser(User user) throws SQLException {

    String sql = "INSERT INTO users "
            + "(full_name, email, password_hash, phone, role) "
            + "VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, user.getFullName());
        stmt.setString(2, user.getEmail());
        stmt.setString(3, user.getPasswordHash());
        stmt.setString(4, user.getPhone());
        stmt.setString(5, user.getRole());

        return stmt.executeUpdate() == 1;
    }
}

    
}
