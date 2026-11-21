package com.sweetbake.controller;

import com.sweetbake.dao.MenuDAO;
import com.sweetbake.model.Category;
import com.sweetbake.model.Option;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "BuilderServlet", urlPatterns = {"/builder"})
public class BuilderServlet extends HttpServlet {

    private MenuDAO menuDAO = new MenuDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int categoryId = Integer.parseInt(request.getParameter("category"));
            Category category = menuDAO.getCategory(categoryId);

            if (category == null) {
                response.sendRedirect("home");
                return;
            }

            Map<String, List<Option>> groups = new LinkedHashMap<>();
            for (Option option : menuDAO.getOptions(categoryId)) {
                if (!groups.containsKey(option.getOptionType())) {
                    groups.put(option.getOptionType(), new ArrayList<>());
                }
                groups.get(option.getOptionType()).add(option);
            }

            request.setAttribute("category", category);
            request.setAttribute("groups", groups);
            request.getRequestDispatcher("/WEB-INF/views/builder.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect("home");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
