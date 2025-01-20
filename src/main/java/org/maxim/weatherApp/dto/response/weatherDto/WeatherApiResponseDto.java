package org.maxim.weatherApp.dto.response.weatherDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherApiResponseDto {
    Integer locationId;
    @JsonProperty("name") String name;
    @JsonProperty("coord") Coord coord;
    @JsonProperty("weather") List<Weather> weather;
    @JsonProperty("main") Main main;
}
