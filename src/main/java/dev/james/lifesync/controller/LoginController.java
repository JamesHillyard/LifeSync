package dev.james.lifesync.controller;

import dev.james.lifesync.database.LifeSyncUserService;
import dev.james.lifesync.exception.AuthenticationException;
import dev.james.lifesync.entity.LifeSyncUser;
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
public class LoginController {

    private final LifeSyncUserService lifeSyncUserService;

    @Autowired
    public LoginController(LifeSyncUserService lifeSyncUserService) {
        this.lifeSyncUserService = lifeSyncUserService;
    }

    Logger LOGGER = Logger.getLogger(LoginController.class.getName());

    private void authenticateUser(String email, String password) throws AuthenticationException {
        String passwordInDatabase = lifeSyncUserService.getUserPassword(email);
        if (!password.equals(passwordInDatabase)) {
            LOGGER.fine("User " + email + " tried to login but their credentials were incorrect.");
            throw new AuthenticationException();
        }
        LOGGER.fine("User " + email + " successfully authenticated.");
    }

    @GetMapping
    public String showLoginPage() {
        return "login";
    }

    @PostMapping
    public String authenticate(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               Model model, RedirectAttributes redirectAttributes) {
        try {
            authenticateUser(email, password);
            LifeSyncUser user = lifeSyncUserService.getUser(email);
            model.addAttribute("user", user);
            return "redirect:/hlsp/nutrition";
        } catch (AuthenticationException e) {
            // Handle authentication failure
            redirectAttributes.addFlashAttribute("loginError", "Invalid Username or Password.");
            return "redirect:/login";
        }
    }
}
