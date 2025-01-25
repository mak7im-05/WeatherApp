package org.maxim.weatherApp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.maxim.weatherApp.dto.request.RegisterRequestDTO;
import org.maxim.weatherApp.entity.User;
import org.maxim.weatherApp.mapper.UserRegistrationMapper;
import org.maxim.weatherApp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;
    private final UserRegistrationMapper userRegistrationMapper;

    @GetMapping
    public String showRegistrationPage(@ModelAttribute("user") RegisterRequestDTO user) {
        return "registration";
    }

    @PostMapping
    public String processRegistration(@ModelAttribute("user") @Valid RegisterRequestDTO registerUser,
                                      BindingResult bindingResult,
                                      Model model,
                                      RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!registerUser.password().equals(registerUser.confirmPassword())) {
            model.addAttribute("error", "Passwords do not match");
            return "registration";
        }

        try {
            User user = userRegistrationMapper.toServiceDto(registerUser);
            userService.registerUser(user);
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please log in.");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "registration";
        }
    }
}
