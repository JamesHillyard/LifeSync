package dev.james.lifesync.controller;

import dev.james.lifesync.database.LifeSyncUserService;
import dev.james.lifesync.entity.LifeSyncUser;
import jakarta.servlet.http.HttpServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.logging.Logger;

@Controller
@RequestMapping("/signup")
public class SignupController extends HttpServlet {

    Logger LOGGER = Logger.getLogger(SignupController.class.getName());

    private final LifeSyncUserService lifeSyncUserService;

    @Autowired
    public SignupController(LifeSyncUserService lifeSyncUserService) {
        this.lifeSyncUserService = lifeSyncUserService;
    }

    @GetMapping
    public String showSignupPage() {
        return "signup";
    }

    @PostMapping
    public String registerUser(@RequestParam("firstname") String firstname,
                               @RequestParam("lastname") String lastname,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password,
                               Model model) {

        if (!emailAvailable(email)) {
            LOGGER.fine("New User " + email + " could not register as email is taken.");
            model.addAttribute("error", "Username " + email + " is already taken.");
            return "signup";
        }

        LifeSyncUser user = new LifeSyncUser(firstname, lastname, email, password);
        saveUser(user);

        LOGGER.fine(String.format("%s %s has successfully registered as %s", firstname, lastname, email));
        model.addAttribute("successMessage", "Registered Successfully");
        return "signup";
    }

    private void saveUser(LifeSyncUser user) {
        lifeSyncUserService.saveUser(user);
    }

    private boolean emailAvailable(String email) {
        // If getting the user by email returns a null object, it wasn't found and therefore available
        return lifeSyncUserService.getUser(email) == null;
    }
}
