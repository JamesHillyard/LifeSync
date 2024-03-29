package dev.james.lifesync.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/hlsp/logout")
@SessionAttributes("user")
public class LogoutController {

    @GetMapping
    public String removeUserSession(SessionStatus status) {
        status.setComplete(); // Complete the session invalidating it
        return "redirect:/login";
    }
}
