package dev.james.lifesync.controller;

import dev.james.lifesync.model.LifeSyncUser;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "LogoutServlet", urlPatterns = "/hlsp/logout")
public class LogoutServlet extends HttpServlet {

    final Logger LOGGER = Logger.getLogger(LogoutServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LifeSyncUser user = (LifeSyncUser) request.getSession().getAttribute("user");
        LOGGER.fine("User " + user.getUsername() + " has logged out.");

        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath() + "login.jsp");
    }
}
