package dev.james.lifesync.controller;

import dev.james.lifesync.dao.LifeSyncUserService;
import dev.james.lifesync.model.LifeSyncUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "PasswordResetServlet", urlPatterns = "/passwordreset")
public class PasswordResetServlet extends HttpServlet {

    final Logger LOGGER = Logger.getLogger(LogoutServlet.class.getName());

    private final LifeSyncUserService lifeSyncUserService;

    @Autowired
    public PasswordResetServlet(LifeSyncUserService lifeSyncUserService) {
        this.lifeSyncUserService = lifeSyncUserService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("passwordreset.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String newPassword = request.getParameter("newPassword");

        LifeSyncUser user = lifeSyncUserService.getUser(username);
        if (user == null) {
            LOGGER.fine("Couldn't reset password as user " + username + " does not exist.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            request.setAttribute("error", "User doesn't exist.");
            request.getRequestDispatcher("passwordreset.jsp").forward(request, response);
            return;
        }

        user.setPassword(newPassword);
        lifeSyncUserService.saveUser(user);

        LOGGER.fine("User " + user.getUsername() + " has changed their password.");

        request.setAttribute("successMessage", "Password Reset Successfully");
        request.getRequestDispatcher("passwordreset.jsp").forward(request, response);
    }
}
