package org.maxim.weatherApp.controller;

import lombok.RequiredArgsConstructor;
import org.maxim.weatherApp.dto.request.LocationRequestDto;
import org.maxim.weatherApp.service.LocationService;
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

    @GetMapping
    public String showLocationSearchPage(@RequestParam("locationName") String locationName,
                                        @RequestParam("login") String login,
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
    public String addNewLocation(@RequestParam("latitude") BigDecimal lat,
                                @RequestParam("longitude") BigDecimal lon,
                                @RequestParam("locationName") String locationName,
                                @RequestParam("login") String login,
                                @RequestAttribute("userId") String userId, Model model) {
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