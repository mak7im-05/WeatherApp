package org.maxim.weatherApp.services;

import org.maxim.weatherApp.clients.OpenWeatherApiClient;
import org.maxim.weatherApp.dto.LocationDto;
import org.maxim.weatherApp.dto.weatherDto.WeatherApiResponseDto;
import org.maxim.weatherApp.entities.Location;
import org.maxim.weatherApp.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class LocationService {

    private final LocationRepository locationRepository;
    private final OpenWeatherApiClient openWeatherApiService;

    @Autowired
    public LocationService(LocationRepository iLocationRepository, OpenWeatherApiClient openWeatherApiService) {
        this.locationRepository = iLocationRepository;
        this.openWeatherApiService = openWeatherApiService;
    }

    public List<WeatherApiResponseDto> findLocationsByUserId(int userId) {
        List<Location> locations = locationRepository.findAllByUserId(userId);
        return getWeatherApiResponseDtoList(locations);
    }

    @Transactional
    public void addWeather(Location location) {
        Optional<Location> existLocation = locationRepository.findByLatitudeAndLongitude(location.getLatitude(), location.getLongitude());
        existLocation.ifPresent(l -> {
            throw new IllegalArgumentException("The location already exists");
        });
        locationRepository.save(location);
    }

    @Transactional
    public void deleteWeather(BigDecimal latitude, BigDecimal longitude) {
        locationRepository.deleteByLatitudeAndLongitude(latitude, longitude);
    }

    public List<LocationDto> findLocationsByCityName(String cityName) {
        return openWeatherApiService.getWeatherByLocation(cityName);
    }

    private List<WeatherApiResponseDto> getWeatherApiResponseDtoList(List<Location> locations) {
        return locations.stream()
                .map(location -> openWeatherApiService.getWeatherByCoordinates(
                        location.getLongitude(),
                        location.getLatitude()))
                .toList();
    }
}
