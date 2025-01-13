package org.maxim.weatherapp.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.maxim.weatherapp.dto.LocationResponseDto;
import org.maxim.weatherapp.dto.weatherDto.WeatherResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OpenWeatherApiService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public OpenWeatherApiService() {
        webClient = WebClient.create("https://api.openweathermap.org/");
        this.objectMapper = new ObjectMapper();
    }

    public List<LocationResponseDto> getWeatherByLocation(String location) {
        Mono<String> responseMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("geo/1.0/direct")
                        .queryParam("q", location)
                        .queryParam("appid", "fcaba8fd655f67eb30b62940d5658bc4")
                        .build())
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class).flatMap(errorBody -> {
                            System.err.println("Ошибка: " + errorBody);
                            return Mono.error(new RuntimeException("Ошибка: " + response.statusCode()));
                        })
                )
                .bodyToMono(String.class);

        return responseMono.map(response -> {
            try {
                return objectMapper.readValue(response,  new TypeReference<List<LocationResponseDto>>(){});
            } catch (Exception e) {
                throw new RuntimeException("Ошибка десериализации JSON" + e.getMessage(), e);
            }
        }).block();
    }

    public WeatherResponseDto getWeatherByCoordinates(BigDecimal lat, BigDecimal lon) {
        Mono<String> responseMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("data/2.5/weather")
                        .queryParam("lon", lon.toString())
                        .queryParam("lat", lat.toString())
                        .queryParam("appid", "fcaba8fd655f67eb30b62940d5658bc4")
                        .queryParam("units", "metric")
                        .build())
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class).flatMap(errorBody -> {
                            System.err.println("Ошибка: " + errorBody);
                            return Mono.error(new RuntimeException("Ошибка: " + response.statusCode()));
                        })
                )
                .bodyToMono(String.class);

        return responseMono.map(response -> {
            try {
                return objectMapper.readValue(response, WeatherResponseDto.class);
            } catch (Exception e) {
                throw new RuntimeException("Ошибка десериализации JSON" + e.getMessage(), e);
            }
        }).block();
    }
}
