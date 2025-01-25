package org.maxim.weatherApp.service;

import lombok.RequiredArgsConstructor;
import org.maxim.weatherApp.client.OpenWeatherApiClient;
import org.maxim.weatherApp.dto.request.LocationRequestDto;
import org.maxim.weatherApp.dto.response.weatherDto.WeatherApiResponseDto;
import org.maxim.weatherApp.entity.Location;
import org.maxim.weatherApp.mapper.LocationMapper;
import org.maxim.weatherApp.repository.LocationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final OpenWeatherApiClient openWeatherApiService;
    private final LocationMapper locationMapper;

    @Transactional(readOnly = true)
    public List<Location> findLocationsByUserId(int userId) {
        return locationRepository.findAllByUserId(userId);
    }

    @Transactional
    public void addWeather(LocationRequestDto location, int userId) {
        BigDecimal lon = location.longitude().setScale(2, RoundingMode.HALF_UP);
        BigDecimal lat = location.latitude().setScale(2, RoundingMode.HALF_UP);
        Optional<Location> existLocation = locationRepository.findByLatitudeAndLongitude(lat, lon);
        if (existLocation.isPresent() && existLocation.get().getUserId() == userId) {
            throw new IllegalArgumentException("The location already exists");
        }

        Location locationEntity = locationMapper.mapTo(location);
        locationEntity.setUserId(userId);
        locationRepository.save(locationEntity);
    }

    @Transactional
    public void deleteWeather(int locationId, int userId) {
        Optional<Location> location = locationRepository.findById(locationId);
        if(location.isPresent()) {
            if (location.get().getUserId() == userId) {
                locationRepository.deleteById(locationId);
                return;
            }
            throw new IllegalArgumentException("A user can't delete someone else's weather");
        }
    }

    @Transactional(readOnly = true)
    public List<LocationRequestDto> findLocationsByCityName(String cityName) {
        return openWeatherApiService.getWeatherByLocation(cityName);
    }

    @Transactional(readOnly = true)
    public List<WeatherApiResponseDto> getWeatherApiResponseDtoList(List<Location> locations) {
        return locations.stream()
                .map(location -> {
                    WeatherApiResponseDto weather = openWeatherApiService.getWeatherByCoordinates(location.getLatitude(), location.getLongitude());
                    weather.setName(location.getName());
                    weather.setLocationId(location.getId());
                    return weather;
                })
                .collect(Collectors.toList());
    }
}
