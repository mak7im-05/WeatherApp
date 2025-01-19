package org.maxim.weatherApp.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.maxim.weatherApp.dto.RegisterRequestDTO;
import org.maxim.weatherApp.dto.UserServiceDTO;
import org.maxim.weatherApp.mapper.UserRegistrationMapper;
import org.maxim.weatherApp.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;
    private final UserRegistrationMapper userRegistrationMapper;

    @GetMapping("/registration")
    public String showRegistrationPage(@ModelAttribute("user") RegisterRequestDTO user) {
        return "registration";
    }

    @PostMapping("/registration")
    public String processRegistration(@ModelAttribute("user") @Valid RegisterRequestDTO RegisterUser,
                                      BindingResult bindingResult,
                                      Model model,
                                      RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!RegisterUser.password().equals(RegisterUser.confirmPassword())) {
            model.addAttribute("error", "Passwords do not match");
            return "registration";
        }

        try {
            UserServiceDTO userServiceDto = userRegistrationMapper.mapTo(RegisterUser);
            userService.registerUser(userServiceDto);
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please log in.");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "registration";
        }
    }
}
