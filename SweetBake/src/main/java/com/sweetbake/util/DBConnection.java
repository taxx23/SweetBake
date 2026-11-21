
package com.sweetbake.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/sweetbake";

    private static final String USER = "root";

    public static Connection getConnection() throws SQLException {
        String password = System.getenv("SWEETBAKE_DB_PASSWORD");

        if (password == null || password.isEmpty()) {
            throw new SQLException("Database password is not configured.");
        }

        return DriverManager.getConnection(URL, USER, password);
    }
}
