package com.sweetbake.controller;

import com.sweetbake.dao.MenuDAO;
import com.sweetbake.dao.UserDAO;
import com.sweetbake.model.Category;
import com.sweetbake.model.Option;
import com.sweetbake.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet(name = "AdminServlet", urlPatterns = {"/admin"})
public class AdminServlet extends HttpServlet {

    private MenuDAO menuDAO = new MenuDAO();
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("categories", menuDAO.getCategories());
            request.setAttribute("options", menuDAO.getAllOptions());
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        request.getRequestDispatcher("/WEB-INF/views/admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("updateCategory".equals(action)) {
                Category category = new Category();
                category.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
                category.setName(request.getParameter("name"));
                category.setDescription(request.getParameter("description"));
                category.setImage(request.getParameter("image"));
                menuDAO.updateCategory(category);
                request.setAttribute("message", "Dessert saved.");

            } else if ("updateOption".equals(action)) {
                int optionId = Integer.parseInt(request.getParameter("optionId"));
                double price = Double.parseDouble(request.getParameter("price"));
                menuDAO.updateOption(optionId, request.getParameter("name"), price);
                request.setAttribute("message", "Option saved.");

            } else if ("addOption".equals(action)) {
                Option option = new Option();
                option.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
                option.setOptionType(request.getParameter("optionType"));
                option.setName(request.getParameter("name"));
                option.setPrice(Double.parseDouble(request.getParameter("price")));
                menuDAO.addOption(option);
                request.setAttribute("message", "Option added.");

            } else if ("removeOption".equals(action)) {
                menuDAO.removeOption(Integer.parseInt(request.getParameter("optionId")));
                request.setAttribute("message", "Option removed.");

            } else if ("addBaker".equals(action)) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                if (userDAO.findByEmail(email) != null) {
                    request.setAttribute("error", "This email is already used.");
                } else if (password == null || password.length() < 8) {
                    request.setAttribute("error", "Password must be at least 8 characters.");
                } else {
                    User baker = new User();
                    baker.setFullName(request.getParameter("fullName"));
                    baker.setEmail(email);
                    baker.setPhone(request.getParameter("phone"));
                    baker.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));
                    baker.setRole("BAKER");
                    userDAO.addUser(baker);
                    request.setAttribute("message", "Baker account added.");
                }
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Please enter a valid number.");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        doGet(request, response);
    }
}
