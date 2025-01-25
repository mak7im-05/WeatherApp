package org.maxim.weatherApp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.maxim.weatherApp.dto.response.weatherDto.WeatherApiResponseDto;
import org.maxim.weatherApp.services.LocationService;
import org.maxim.weatherApp.services.SessionService;
import org.maxim.weatherApp.services.UserService;
import org.maxim.weatherApp.utils.CookieUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {

    private final LocationService locationService;
    private final SessionService sessionService;
    private final UserService userService;

    @GetMapping
    public String showHomePage(@RequestAttribute(name = "userId") String userId, Model model) {
        String login = userService.getUserLoginById(Integer.parseInt(userId));
        List<WeatherApiResponseDto> locations = locationService.findLocationsByUserId(Integer.parseInt(userId));

        model.addAttribute("login", login);
        model.addAttribute("locations", locations);
        model.addAttribute("userId", userId);

        return "home";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        String sessionId = CookieUtils.findCookieByName(request, "sessionId");

        UUID sessionUuid = UUID.fromString(sessionId);
        sessionService.deleteSessionById(sessionUuid);

        CookieUtils.deleteCookie(response, sessionId);
        return "redirect:/login";
    }

    @PostMapping
    public String deleteWeatherCard(@RequestParam(name = "locationId") int locationId,
                                    @RequestParam(name = "userId") int userId) {
        locationService.deleteWeather(locationId, userId);
        return "redirect:/home";
    }
}
