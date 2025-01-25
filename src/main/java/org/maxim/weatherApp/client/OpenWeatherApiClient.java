package org.maxim.weatherApp.client;

import lombok.extern.slf4j.Slf4j;
import org.maxim.weatherApp.dto.request.LocationRequestDto;
import org.maxim.weatherApp.dto.response.weatherDto.WeatherApiResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class OpenWeatherApiClient {

    private static final String BASE_URL = "https://api.openweathermap.org/";
    private final WebClient webClient;

    @Value("${api.key}")
    private static String apiKey;

    public OpenWeatherApiClient() {
        webClient = WebClient.create(BASE_URL);
    }

    public OpenWeatherApiClient(WebClient webClient) {
        this.webClient = webClient;
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException("API key is not loaded. Please check your configuration.");
        }
    }

    public List<LocationRequestDto> getWeatherByLocation(String location) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("geo/1.0/direct")
                        .queryParam("q", location)
                        .queryParam("appid", apiKey)
                        .queryParam("limit", 5)
                        .build())
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class).flatMap(errorBody -> Mono.error(new RuntimeException("error: " + response.statusCode())))
                )
                .bodyToMono(new ParameterizedTypeReference<List<LocationRequestDto>>() {
                })
                .block();
    }

    public WeatherApiResponseDto getWeatherByCoordinates(BigDecimal lat, BigDecimal lon) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("data/2.5/weather")
                        .queryParam("lon", lon.toString())
                        .queryParam("lat", lat.toString())
                        .queryParam("appid", apiKey)
                        .queryParam("units", "metric")
                        .build())
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class).flatMap(errorBody -> Mono.error(new RuntimeException("Ошибка: " + response.statusCode())))
                )
                .bodyToMono(WeatherApiResponseDto.class)
                .block();
    }
}