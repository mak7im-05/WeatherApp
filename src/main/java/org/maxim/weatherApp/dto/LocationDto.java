package org.maxim.weatherApp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LocationDto(
    @JsonProperty("name")
    String name,
    @JsonProperty("lat")
    BigDecimal latitude,
    @JsonProperty("lon")
    BigDecimal longitude
) {}
