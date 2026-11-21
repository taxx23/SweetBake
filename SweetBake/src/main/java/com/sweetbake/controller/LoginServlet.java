package com.sweetbake.controller;

import com.sweetbake.dao.UserDAO;
import com.sweetbake.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            User user = userDAO.findByEmail(email);

            if (user == null || password == null || !BCrypt.checkpw(password, user.getPasswordHash())) {
                request.setAttribute("error", "Wrong email or password.");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
                return;
            }

            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            if (user.getRole().equals("ADMIN")) {
                response.sendRedirect("admin");
            } else if (user.getRole().equals("BAKER")) {
                response.sendRedirect("baker");
            } else {
                response.sendRedirect("home");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
