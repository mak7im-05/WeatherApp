package org.maxim.weatherApp.controllers;

import org.maxim.weatherApp.dto.LocationDto;
import org.maxim.weatherApp.services.LocationService;
import org.maxim.weatherApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class SearchController {

    private final LocationService locationService;
    private final UserService userService;

    @Autowired
    public SearchController(LocationService locationService, UserService userService) {
        this.locationService = locationService;
        this.userService = userService;
    }

    @GetMapping("/weather-search")
    public String showWeatherSearchPage(@RequestParam(name = "locationName") String locationName,
                                        @RequestAttribute(name = "userId") String userId,
                                        Model model) {
        String login = userService.getUserLoginById(Integer.parseInt(userId));
        model.addAttribute("login", login);

        List<LocationDto> locations = locationService.findLocationsByCityName(locationName);
        model.addAttribute("locations", locations);
        return "weather-search";
    }

    @PostMapping("/add")
    public String addNewWeather(@RequestParam(name = "latitude") BigDecimal lat,
                                @RequestParam(name = "longitude") BigDecimal lon,
                                @RequestParam(name = "locationName") String locationName,
                                @RequestAttribute(name = "userId") String userId, Model model) {

        LocationDto locationDto = new LocationDto(locationName, lat, lon);
        try {
            locationService.addWeather(locationDto, Integer.parseInt(userId));
        } catch (IllegalArgumentException e) {
            String login = userService.getUserLoginById(Integer.parseInt(userId));
            model.addAttribute("login", login);
            model.addAttribute("error", e.getMessage());
            return "weather-search";
        }
        return "redirect:/home";
    }
}