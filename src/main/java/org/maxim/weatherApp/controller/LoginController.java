package org.maxim.weatherApp.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.maxim.weatherApp.dto.request.UserServiceRequestDto;
import org.maxim.weatherApp.service.SessionService;
import org.maxim.weatherApp.service.UserService;
import org.maxim.weatherApp.util.CookieUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final SessionService sessionService;
    private final UserService userService;

    @GetMapping
    public String showLoginPage(@ModelAttribute("user") UserServiceRequestDto user) {
        return "login";
    }

    @PostMapping
    public String handleLogin(@ModelAttribute("user") @Valid UserServiceRequestDto user,
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

