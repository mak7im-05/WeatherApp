package org.maxim.weatherapp.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.maxim.weatherapp.dto.RegisterRequestDTO;
import org.maxim.weatherapp.mapper.IUserRegistrationMapper;
import org.maxim.weatherapp.dto.UserServiceDTO;
import org.maxim.weatherapp.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;
    private final IUserRegistrationMapper userRegistrationMapper;

    @GetMapping("/registration")
    public String showRegistrationPage(@ModelAttribute("user") RegisterRequestDTO user) {
        return "registration";
    }

    @PostMapping("/registration")
    public String processRegistration(@ModelAttribute("user") @Valid RegisterRequestDTO RegisterUser,
                                      BindingResult bindingResult,
                                      Model model) {
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
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "registration";
        }
    }
}
