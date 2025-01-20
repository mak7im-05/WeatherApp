package org.maxim.weatherApp.controllers;

import lombok.RequiredArgsConstructor;
import org.maxim.weatherApp.dto.request.LocationRequestDto;
import org.maxim.weatherApp.services.LocationService;
import org.maxim.weatherApp.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/weather-search")
public class SearchController {

    private final LocationService locationService;
    private final UserService userService;

    @GetMapping
    public String showWeatherSearchPage(@RequestParam(name = "locationName") String locationName,
                                        @RequestParam(name = "login") String login,
                                        @ModelAttribute("locationDto") LocationRequestDto locationDto,
                                        Model model) {
        if (locationName.trim().isEmpty()) {
            model.addAttribute("error", "Please enter a valid location name");
            return "weather-search";
        }

        List<LocationRequestDto> locations = locationService.findLocationsByCityName(locationName);

        model.addAttribute("login", login);
        model.addAttribute("locations", locations);

        return "weather-search";
    }

    @PostMapping("/add")
    public String addNewWeather(@RequestParam(name = "latitude") BigDecimal lat,
                                @RequestParam(name = "longitude") BigDecimal lon,
                                @RequestParam(name = "locationName") String locationName,
                                @RequestParam(name = "login") String login,
                                @RequestAttribute(name = "userId") String userId, Model model) {
        LocationRequestDto locationDto = new LocationRequestDto(locationName, lat, lon);

        try {
            locationService.addWeather(locationDto, Integer.parseInt(userId));
        } catch (IllegalArgumentException e) {
            model.addAttribute("login", login);
            model.addAttribute("error", e.getMessage());
            return "weather-search";
        }
        return "redirect:/home";
    }
}