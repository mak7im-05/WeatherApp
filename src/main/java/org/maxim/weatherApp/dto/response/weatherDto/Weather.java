package org.maxim.weatherApp.dto.response.weatherDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Weather (@JsonProperty("description") String description) {
}
