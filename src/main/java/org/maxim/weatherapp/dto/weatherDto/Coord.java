package org.maxim.weatherapp.dto.weatherDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Coord(
        @JsonProperty("lat") BigDecimal lat,
        @JsonProperty("lon") BigDecimal lon) {
}
