
package com.sweetbake.util;

import com.sweetbake.dao.UserDAO;
import com.sweetbake.model.User;

public class TestUserDAO {

    public static void main(String[] args) {
        try {
            UserDAO dao = new UserDAO();

            User user = dao.findByEmail("customer@sweetbake.test");

            if (user != null) {
                System.out.println("SUCCESS! User found!");
                System.out.println("Name: " + user.getFullName());
                System.out.println("Email: " + user.getEmail());
                System.out.println("Role: " + user.getRole());
            } else {
                System.out.println("User not found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
