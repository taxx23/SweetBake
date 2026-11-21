package com.sweetbake.controller;

import com.sweetbake.dao.UserDAO;
import com.sweetbake.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String error = null;

        try {
            if (fullName == null || fullName.isBlank() || email == null || email.isBlank()) {
                error = "Please fill in all fields.";
            } else if (phone == null || !phone.matches("05[0-9]{8}")) {
                error = "Phone number must start with 05 and have 10 digits.";
            } else if (password == null || password.length() < 8) {
                error = "Password must be at least 8 characters.";
            } else if (!password.equals(confirmPassword)) {
                error = "The two passwords are not the same.";
            } else if (userDAO.findByEmail(email) != null) {
                error = "This email is already registered.";
            }

            if (error != null) {
                request.setAttribute("error", error);
                request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
                return;
            }

            User user = new User();
            user.setFullName(fullName);
            user.setEmail(email);
            user.setPhone(phone);
            user.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));
            user.setRole("CUSTOMER");
            userDAO.addUser(user);

            response.sendRedirect("login?registered=yes");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
