
package com.sweetbake.util;

import com.sweetbake.dao.SettingsDAO;

public class TestSettingsDAO {

    public static void main(String[] args) {
        try {
            SettingsDAO dao = new SettingsDAO();

            int capacity = dao.getDailyCapacity();

            System.out.println("SUCCESS! Daily capacity: " + capacity);

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
