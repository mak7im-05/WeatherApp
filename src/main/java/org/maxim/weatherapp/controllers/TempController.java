package org.maxim.weatherapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.maxim.weatherapp.dto.LocationDto;
import org.maxim.weatherapp.dto.LocationResponseDto;
import org.maxim.weatherapp.entities.Location;
import org.maxim.weatherapp.services.LocationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.List;


@Controller
public class TempController {

    private final LocationService locationService;

    public TempController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/temp")
    public String showTempPage() {
//        OpenWeatherApiService openWeatherApiService = new OpenWeatherApiService();
//        WeatherResponseDto london = openWeatherApiService.fi(new BigDecimal(12), new BigDecimal(123));
//        System.out.println(london);

//        Location location = new Location();
//        location.setName("London");
//        location.setLatitude(BigDecimal.valueOf(1));
//        location.setLongitude(BigDecimal.valueOf(2));
//        location.setUserId(1);
//        locationService.deleteWeather(BigDecimal.valueOf(1), BigDecimal.valueOf(2));

        locationService.findLocationsByCityName("London");

        return "temp";
    }
}
