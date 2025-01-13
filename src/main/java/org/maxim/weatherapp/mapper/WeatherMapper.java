package org.maxim.weatherapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.maxim.weatherapp.dto.LocationDto;
import org.maxim.weatherapp.dto.weatherDto.Weather;
import org.maxim.weatherapp.dto.weatherDto.WeatherResponseDto;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WeatherMapper {
    @Mapping(source = "cityName", target = "name")
    @Mapping(source = "main.temp", target = "temp")
    @Mapping(source = "main.feelsLike", target = "feelsLike")
    @Mapping(source = "weather", target = "description", qualifiedByName = "extractFirstWeatherDescription")
    @Mapping(source = "main.humidity", target = "humidity")
    LocationDto toLocationDto(WeatherResponseDto weatherResponseDto);

    @Named("extractFirstWeatherDescription")
    default String extractFirstWeatherDescription(List<Weather> weather) {
        return (weather != null && !weather.isEmpty()) ? weather.getFirst().description() : null;
    }
}

