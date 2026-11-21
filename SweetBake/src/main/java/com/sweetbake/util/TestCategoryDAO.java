
package com.sweetbake.util;

import com.sweetbake.dao.CategoryDAO;
import com.sweetbake.model.Category;

public class TestCategoryDAO {

    public static void main(String[] args) {
        try {
            CategoryDAO dao = new CategoryDAO();

            for (Category category : dao.getAllCategories()) {
                System.out.println(category.getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
