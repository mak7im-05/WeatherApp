package org.maxim.weatherApp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.flywaydb.core.Flyway;
import org.maxim.weatherApp.dto.weatherDto.WeatherApiResponseDto;
import org.maxim.weatherApp.services.LocationService;
import org.maxim.weatherApp.services.SessionService;
import org.maxim.weatherApp.services.UserService;
import org.maxim.weatherApp.utils.CookieUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

        List<WeatherApiResponseDto> locations1 = locationService.findLocationsByUserId(Integer.parseInt(userId));
        model.addAttribute("locations", locations1);

        return "home";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        String sessionId = CookieUtils.findCookieByName(request, "sessionId");
        sessionService.deleteSessionById(sessionId);
        return "redirect:/login";
    }

    @PostMapping("/home")
    public String deleteWeatherCard(@RequestParam(name = "lat") BigDecimal lat,
                                    @RequestParam(name = "lon") BigDecimal lon) {
        locationService.deleteWeather(lat, lon);
        return "redirect:/home";
    }
}
