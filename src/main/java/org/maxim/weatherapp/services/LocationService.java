package org.maxim.weatherapp.services;

import org.maxim.weatherapp.dto.LocationDto;
import org.maxim.weatherapp.dto.LocationResponseDto;
import org.maxim.weatherapp.dto.weatherDto.WeatherResponseDto;
import org.maxim.weatherapp.entities.Location;
import org.maxim.weatherapp.mapper.WeatherMapper;
import org.maxim.weatherapp.repositories.ILocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class LocationService {

    private final ILocationRepository locationRepository;
    private final OpenWeatherApiService openWeatherApiService;
    private final WeatherMapper weatherMapper;

    @Autowired
    public LocationService(ILocationRepository iLocationRepository, OpenWeatherApiService openWeatherApiService, WeatherMapper weatherMapper) {
        this.locationRepository = iLocationRepository;
        this.openWeatherApiService = openWeatherApiService;
        this.weatherMapper = weatherMapper;
    }

    public List<LocationDto> findLocationsByUserId(int userId) {
        List<Location> locationsById = locationRepository.findAllByUserId(userId);
        return getLocationByCoordinates(locationsById);
    }

    private List<LocationDto> getLocationByCoordinates(List<Location> locationsById) {
        List<WeatherResponseDto> weatherResponseDtos = mapLocationsToWeatherResponses(locationsById);
        return mapWeatherResponseToLocationDto(weatherResponseDtos);
    }

    private List<LocationDto> mapWeatherResponseToLocationDto(List<WeatherResponseDto> weatherResponseDtos) {
        List<LocationDto> locationDtos = new ArrayList<>();
        for (WeatherResponseDto location : weatherResponseDtos) {
            locationDtos.add(weatherMapper.toLocationDto(location));
        }
        return locationDtos;
    }

    private List<WeatherResponseDto> mapLocationsToWeatherResponses(List<Location> locationsById) {
        List<WeatherResponseDto> weatherResponseDtos = new ArrayList<>();
        for (Location location : locationsById) {
            BigDecimal currentLatitude = location.getLatitude();
            BigDecimal currentLongitude = location.getLongitude();
            weatherResponseDtos.add(openWeatherApiService.getWeatherByCoordinates(currentLongitude, currentLatitude));
        }
        return weatherResponseDtos;
    }

    public List<LocationResponseDto> findLocationsByCityName(String cityName) {
        List<LocationResponseDto> list = openWeatherApiService.getWeatherByLocation(cityName);
        return list;
    }

    public void addWeather(Location location) {
        locationRepository.save(location);
    }

    public void deleteWeather(BigDecimal latitude, BigDecimal longitude) {
        locationRepository.deleteByLatitudeAndLongitude(latitude, longitude);
    }
}
