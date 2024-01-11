package dev.james.lifesync.controller;

import dev.james.lifesync.dao.LifeSyncUserService;
import dev.james.lifesync.exception.AuthenticationException;
import dev.james.lifesync.model.LifeSyncUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.logging.Logger;

@Controller
@RequestMapping("/login")
@SessionAttributes("user")
public class LoginServlet {

    private final LifeSyncUserService lifeSyncUserService;

    @Autowired
    public LoginServlet(LifeSyncUserService lifeSyncUserService) {
        this.lifeSyncUserService = lifeSyncUserService;
    }

    Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

    private void authenticateUser(String username, String password) throws AuthenticationException {
        String passwordInDatabase = lifeSyncUserService.getUserPassword(username);
        if (!password.equals(passwordInDatabase)) {
            LOGGER.fine("User " + username + " tried to login but their credentials were incorrect.");
            throw new AuthenticationException();
        }
        LOGGER.fine("User " + username + " successfully authenticated.");
    }

    @GetMapping
    public String showLoginPage() {
        return "login";
    }

    @PostMapping
    public String authenticate(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               Model model, RedirectAttributes redirectAttributes) {
        try {
            authenticateUser(username, password);
            LifeSyncUser user = lifeSyncUserService.getUser(username);
            model.addAttribute("user", user);
            return "redirect:/hlsp/dashboard";
        } catch (AuthenticationException e) {
            // Handle authentication failure
            redirectAttributes.addFlashAttribute("loginError", "Invalid Username or Password.");
            return "redirect:/login";
        }
    }
}
