package org.maxim.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LocationResponseDto (
    @JsonProperty("name")
    String name,
    @JsonProperty("lat")
    BigDecimal latitude,
    @JsonProperty("lon")
    BigDecimal longitude
) {}
