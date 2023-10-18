package dev.james.lifesync.controller;

import dev.james.lifesync.exception.AuthenticationException;
import dev.james.lifesync.model.LifeSyncUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private void authenticateUser(String username, String password) throws AuthenticationException {
        if (!username.equals("James") && !password.equals("James")) {
            throw new AuthenticationException();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        LifeSyncUser user = new LifeSyncUser(username);

        try {
            authenticateUser(username, password);
        } catch (AuthenticationException e) {
            // TODO: A user-friendly error should be shown on the home page here
            throw new RuntimeException(e);
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        // User authenticated successfully. Take them to the dashboard
        response.sendRedirect(request.getContextPath() + "/dashboard");
    }
}
