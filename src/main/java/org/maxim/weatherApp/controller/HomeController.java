package org.maxim.weatherApp.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.maxim.weatherApp.dto.response.weatherDto.WeatherApiResponseDto;
import org.maxim.weatherApp.entity.Location;
import org.maxim.weatherApp.service.LocationService;
import org.maxim.weatherApp.service.SessionService;
import org.maxim.weatherApp.service.UserService;
import org.maxim.weatherApp.util.CookieUtils;
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
    public String showHomePage(@RequestAttribute("userId") String userId, Model model) {
        String login = userService.getUserLoginById(Integer.parseInt(userId));
        List<Location> locations = locationService.findLocationsByUserId(Integer.parseInt(userId));
        List<WeatherApiResponseDto> weathers = locationService.getWeatherApiResponseDtoList(locations);

        model.addAttribute("login", login);
        model.addAttribute("locations", weathers);
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
    public String deleteWeatherCard(@RequestParam("locationId") int locationId,
                                    @RequestParam("userId") int userId) {
        locationService.deleteWeather(locationId, userId);
        return "redirect:/home";
    }
}
