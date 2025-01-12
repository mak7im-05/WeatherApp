package org.maxim.weatherapp.dto.weatherDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Main (
        @JsonProperty("temp") int temp,
        @JsonProperty("humidity") int humidity,
        @JsonProperty("feels_like") int feelsLike) {
}
