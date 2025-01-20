package org.maxim.weatherApp.dto.response.weatherDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Main (
        @JsonProperty("temp") Integer temp,
        @JsonProperty("humidity") Integer humidity,
        @JsonProperty("feels_like") Integer feelsLike) {
}
