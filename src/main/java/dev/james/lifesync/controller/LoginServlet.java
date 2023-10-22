package dev.james.lifesync.controller;

import dev.james.lifesync.dao.LifeSyncUserService;
import dev.james.lifesync.exception.AuthenticationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private final LifeSyncUserService lifeSyncUserService;

    @Autowired
    public LoginServlet(LifeSyncUserService lifeSyncUserService) {
        this.lifeSyncUserService = lifeSyncUserService;
    }

    Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

    private void authenticateUser(String username, String password) throws AuthenticationException {
        String passwordInDatabase = lifeSyncUserService.getUserPassword(username);
        if (!password.equals(passwordInDatabase)) {
            throw new AuthenticationException();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            authenticateUser(username, password);
            LOGGER.fine("User " + username + " successfully authenticated.");
        } catch (AuthenticationException e) {
            LOGGER.fine("User " + username + " tried to login but their credentials were incorrect.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            request.setAttribute("loginError", "Invalid Username or Password.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", lifeSyncUserService.getUser(username));
        // User authenticated successfully. Take them to the dashboard
        response.sendRedirect(request.getContextPath() + "/dashboard");
    }
}
