package org.maxim.weatherApp.clients;

import org.maxim.weatherApp.dto.LocationDto;
import org.maxim.weatherApp.dto.weatherDto.WeatherApiResponseDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

@Service
public class OpenWeatherApiClient {

    private static final String BASE_URL = "https://api.openweathermap.org/";
    private static String apiKey;
    private final WebClient webClient;

    static {
        loadProperties();
    }

    public OpenWeatherApiClient() {
        webClient = WebClient.create(BASE_URL);
    }

    public OpenWeatherApiClient(WebClient webClient) {
        this.webClient = webClient;
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException("API key is not loaded. Please check your configuration.");
        }
    }

    private static void loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = OpenWeatherApiClient.class.getResourceAsStream("/application.properties")) {
            properties.load(input);
            apiKey = properties.getProperty("api.key");
        } catch (IOException e) {
            System.err.println("Failed to load API key from configuration file: " + e.getMessage());
            apiKey = null;
        }
    }

    public List<LocationDto> getWeatherByLocation(String location) {
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
                        response -> response.bodyToMono(String.class).flatMap(errorBody -> {
                            System.err.println("error: " + errorBody);
                            return Mono.error(new RuntimeException("error: " + response.statusCode()));
                        })
                )
                .bodyToMono(new ParameterizedTypeReference<List<LocationDto>>() {
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
                        response -> response.bodyToMono(String.class).flatMap(errorBody -> {
                            System.err.println("Ошибка: " + errorBody);
                            return Mono.error(new RuntimeException("Ошибка: " + response.statusCode()));
                        })
                )
                .bodyToMono(WeatherApiResponseDto.class)
                .block();
    }
}