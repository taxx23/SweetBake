
package com.sweetbake.util;

import com.sweetbake.dao.OptionDAO;
import com.sweetbake.model.Option;

public class TestOptionDAO {

    public static void main(String[] args) {
        try {
            OptionDAO dao = new OptionDAO();

            for (Option option : dao.getOptionsByCategory(1)) {
                System.out.println(
                    option.getOptionType() + " | " +
                    option.getName() + " | " +
                    option.getPrice()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
