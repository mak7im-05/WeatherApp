package org.maxim.weatherapp.dto;

public record LocationDto (
        String name,
        int temp,
        int feelsLike,
        String description,
        String humidity
) {
}
