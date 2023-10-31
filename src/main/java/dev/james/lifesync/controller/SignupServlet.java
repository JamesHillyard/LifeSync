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

@WebServlet(name = "SignupServlet", urlPatterns = "/signup")
public class SignupServlet extends HttpServlet {

    Logger LOGGER = Logger.getLogger(SignupServlet.class.getName());

    private final LifeSyncUserService lifeSyncUserService;

    @Autowired
    public SignupServlet(LifeSyncUserService lifeSyncUserService) {
        this.lifeSyncUserService = lifeSyncUserService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("signup.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (!usernameAvailable(username)) {
            LOGGER.fine("New User " + username + " could not register as username is taken.");
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            request.setAttribute("error", "Username " + username + " is already taken.");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        LifeSyncUser user = new LifeSyncUser(firstname, lastname, username, password);
        saveUser(user);

        LOGGER.fine(String.format("%s %s has successfully registered as %s", firstname, lastname, username));
        response.setStatus(HttpServletResponse.SC_CREATED);

        request.setAttribute("successMessage", "Registered Successfully");
        request.getRequestDispatcher("signup.jsp").forward(request, response);
    }

    private void saveUser(LifeSyncUser user) {
        lifeSyncUserService.saveUser(user);
    }

    private boolean usernameAvailable(String username) {
        // If getting the user by username returns a null object, it wasn't found and therefore available
        return lifeSyncUserService.getUser(username) == null;
    }
}
