package org.maxim.weatherApp.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.maxim.weatherApp.dto.UserServiceDTO;
import org.maxim.weatherApp.services.SessionService;
import org.maxim.weatherApp.services.UserService;
import org.maxim.weatherApp.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class LoginController {

    private final SessionService sessionService;
    private final UserService userService;

    @Autowired
    public LoginController(SessionService sessionService, UserService userService) {
        this.sessionService = sessionService;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginPage(@ModelAttribute("user") UserServiceDTO user) {
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("user") @Valid UserServiceDTO user,
                              BindingResult bindingResult,
                              Model model, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        try {
            int userId = userService.authenticateUser(user);
            UUID sessionId = sessionService.create(userId);
            Cookie cookie = CookieUtils.createCookieWithSessionId(sessionId);
            response.addCookie(cookie);
            return "redirect:/home";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }
}

