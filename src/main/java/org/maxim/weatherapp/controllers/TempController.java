package org.maxim.weatherapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.maxim.weatherapp.dto.weatherDto.WeatherResponseDto;
import org.maxim.weatherapp.services.OpenWeatherApiService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;

@Controller
public class TempController {
    private final ObjectMapper mapper;

    public TempController() {
        this.mapper = new ObjectMapper();
    }

    @GetMapping("/temp")
    public String showTempPage() {
        OpenWeatherApiService openWeatherApiService = new OpenWeatherApiService();
        WeatherResponseDto london = openWeatherApiService.fi(new BigDecimal(12), new BigDecimal(123));
        System.out.println(london);
        return "temp";
    }
}
