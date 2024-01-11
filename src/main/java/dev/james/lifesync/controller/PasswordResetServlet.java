package dev.james.lifesync.controller;

import dev.james.lifesync.dao.LifeSyncUserService;
import dev.james.lifesync.model.LifeSyncUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.logging.Logger;

@Controller
@RequestMapping("/passwordreset")
public class PasswordResetServlet {

    final Logger LOGGER = Logger.getLogger(LogoutServlet.class.getName());

    private final LifeSyncUserService lifeSyncUserService;

    @Autowired
    public PasswordResetServlet(LifeSyncUserService lifeSyncUserService) {
        this.lifeSyncUserService = lifeSyncUserService;
    }

    @GetMapping
    public String showPasswordResetPage() {
        return "passwordreset";
    }

    @PostMapping
    public String resetPassword(@RequestParam("username") String username,
                                @RequestParam("newPassword") String newPassword,
                                Model model) {

        LifeSyncUser user = lifeSyncUserService.getUser(username);
        if (user == null) {
            LOGGER.fine("Couldn't reset password as user " + username + " does not exist.");
            model.addAttribute("error", "User doesn't exist.");
            return "passwordreset";
        }

        user.setPassword(newPassword);
        lifeSyncUserService.saveUser(user);

        LOGGER.fine("User " + user.getUsername() + " has changed their password.");

        model.addAttribute("successMessage", "Password Reset Successfully");
        return "passwordreset";
    }
}
