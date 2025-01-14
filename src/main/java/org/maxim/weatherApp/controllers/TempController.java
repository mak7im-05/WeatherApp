package org.maxim.weatherApp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.maxim.weatherApp.dto.LocationDto;
import org.maxim.weatherApp.services.LocationService;
import org.maxim.weatherApp.services.SessionService;
import org.maxim.weatherApp.utils.CookieUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class TempController {

    private final LocationService locationService;
    private final SessionService sessionService;

    public TempController(LocationService locationService, SessionService sessionService, SessionService sessionService1) {
        this.locationService = locationService;
        this.sessionService = sessionService1;
    }

    @GetMapping("/temp")
    public String showTempPage() {
//        OpenWeatherApiClient openWeatherApiService = new OpenWeatherApiClient();
//        WeatherApiResponseDto london = openWeatherApiService.fi(new BigDecimal(12), new BigDecimal(123));
//        System.out.println(london);
//
//        Location location = new Location();
//        location.setName("London");
//        location.setLatitude(BigDecimal.valueOf(1));
//        location.setLongitude(BigDecimal.valueOf(2));
//        location.setUserId(1);
//        try {
//            locationService.addWeather(location);
//
//        } catch (IllegalArgumentException e) {
//            System.out.println(e.getMessage());
//            return "error";
//        }

        List<LocationDto> london = locationService.findLocationsByCityName("London");
        System.out.println(london);
        return "temp";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        String sessionId = CookieUtils.findCookieByName(request, "sessionId");
        sessionService.deleteSessionById(sessionId);
        return "redirect:login";
    }
}
