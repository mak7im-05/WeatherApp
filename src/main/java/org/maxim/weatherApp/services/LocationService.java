package org.maxim.weatherApp.services;

import org.maxim.weatherApp.clients.OpenWeatherApiClient;
import org.maxim.weatherApp.dto.LocationDto;
import org.maxim.weatherApp.dto.weatherDto.WeatherApiResponseDto;
import org.maxim.weatherApp.entities.Location;
import org.maxim.weatherApp.mapper.LocationMapper;
import org.maxim.weatherApp.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class LocationService {

    private final LocationRepository locationRepository;
    private final OpenWeatherApiClient openWeatherApiService;
    private final LocationMapper locationMapper;

    @Autowired
    public LocationService(LocationRepository iLocationRepository, OpenWeatherApiClient openWeatherApiService, LocationMapper locationMapper) {
        this.locationRepository = iLocationRepository;
        this.openWeatherApiService = openWeatherApiService;
        this.locationMapper = locationMapper;
    }

    public List<WeatherApiResponseDto> findLocationsByUserId(int userId) {
        List<Location> locations = locationRepository.findAllByUserId(userId);
        return getWeatherApiResponseDtoList(locations);
    }

    @Transactional
    public void addWeather(LocationDto location, int userId) {
        BigDecimal lon = location.longitude().setScale(2, RoundingMode.HALF_UP);
        BigDecimal lat = location.latitude().setScale(2, RoundingMode.HALF_UP);
        Optional<Location> existLocation = locationRepository.findByLatitudeAndLongitude(lat, lon);
        if(existLocation.isPresent() && existLocation.get().getUserId() == userId) {
            throw new IllegalArgumentException("The location already exists");
        }

        Location locationEntity = locationMapper.mapTo(location);
        locationEntity.setUserId(userId);
        locationRepository.save(locationEntity);
    }

    @Transactional
    public void deleteWeather(BigDecimal latitude, BigDecimal longitude) {
        BigDecimal lon = longitude.setScale(2, RoundingMode.HALF_UP);
        BigDecimal lat = latitude.setScale(2, RoundingMode.HALF_UP);
        locationRepository.deleteByLatitudeAndLongitude(lat, lon);
    }

    public List<LocationDto> findLocationsByCityName(String cityName) {
        return openWeatherApiService.getWeatherByLocation(cityName);
    }

    private List<WeatherApiResponseDto> getWeatherApiResponseDtoList(List<Location> locations) {
        List<WeatherApiResponseDto> weathers = new ArrayList<>();
        for (Location location : locations) {
            WeatherApiResponseDto weather = openWeatherApiService.getWeatherByCoordinates(location.getLatitude(), location.getLongitude());
            weather.setName(location.getName());
            weathers.add(weather);
        }
        return weathers;
    }
}
