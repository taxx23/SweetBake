package com.sweetbake.filter;

import com.sweetbake.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/builder", "/cart", "/my-orders", "/baker", "/admin"})
public class AuthFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String path = request.getServletPath();
        String role = user.getRole();
        boolean allowed;

        if (path.equals("/admin")) {
            allowed = role.equals("ADMIN");
        } else if (path.equals("/baker")) {
            allowed = role.equals("BAKER") || role.equals("ADMIN");
        } else {
            allowed = role.equals("CUSTOMER");
        }

        if (allowed) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }
}
