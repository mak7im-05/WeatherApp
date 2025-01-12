package org.maxim.weatherapp.dto.weatherDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherResponseDto(
        @JsonProperty("name") String cityName,
        @JsonProperty("coord") Coord coord,
        @JsonProperty("weather") List<Weather> weather,
        @JsonProperty("main") Main main
) {
}
