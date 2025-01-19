package org.maxim.weatherApp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.maxim.weatherApp.dto.weatherDto.WeatherApiResponseDto;
import org.maxim.weatherApp.services.LocationService;
import org.maxim.weatherApp.services.SessionService;
import org.maxim.weatherApp.services.UserService;
import org.maxim.weatherApp.utils.CookieUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class HomeController {

    private final LocationService locationService;
    private final SessionService sessionService;
    private final UserService userService;

    public HomeController(LocationService locationService, SessionService sessionService, UserService userService) {
        this.locationService = locationService;
        this.sessionService = sessionService;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String showHomePage(Model model,
                               @RequestAttribute(name = "userId") String userId) {
        String login = userService.getUserLoginById(Integer.parseInt(userId));
        model.addAttribute("login", login);

        List<WeatherApiResponseDto> locations = locationService.findLocationsByUserId(Integer.parseInt(userId));
        model.addAttribute("locations", locations);
        model.addAttribute("userId", userId);

        return "home";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        String sessionId = CookieUtils.findCookieByName(request, "sessionId");
        sessionService.deleteSessionById(sessionId);
        return "redirect:/login";
    }

    @PostMapping("/home")
    public String deleteWeatherCard(@RequestParam(name = "locationId") int locationId,
                                    @RequestParam(name = "userId") int userId) {
        locationService.deleteWeather(locationId, userId);
        return "redirect:/home";
    }
}
