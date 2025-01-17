import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.maxim.weatherApp.clients.OpenWeatherApiClient;
import org.maxim.weatherApp.dto.LocationDto;
import org.maxim.weatherApp.dto.weatherDto.Coord;
import org.maxim.weatherApp.dto.weatherDto.Main;
import org.maxim.weatherApp.dto.weatherDto.Weather;
import org.maxim.weatherApp.dto.weatherDto.WeatherApiResponseDto;
import org.maxim.weatherApp.entities.Location;
import org.maxim.weatherApp.mapper.LocationMapper;
import org.maxim.weatherApp.repositories.LocationRepository;
import org.maxim.weatherApp.services.LocationService;
import org.mockito.Mockito;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Component
class OpenWeatherIntegrationTest {

    private OpenWeatherApiClient openWeatherApiClientMock;
    private LocationRepository locationRepositoryMock;
    private LocationMapper locationMapperMock;

    private LocationService locationService;

    @BeforeEach
    void setUp() {
        openWeatherApiClientMock = Mockito.mock(OpenWeatherApiClient.class);
        locationRepositoryMock = Mockito.mock(LocationRepository.class);
        locationMapperMock = Mockito.mock(LocationMapper.class);
        locationService = new LocationService(locationRepositoryMock, openWeatherApiClientMock, locationMapperMock);
    }

    @Test
    void GetWeatherByCoordinates_Success() {
        List<Location> locationList = List.of(
                new Location(1, "London", 1, BigDecimal.valueOf(1), BigDecimal.valueOf(1))
        );
        WeatherApiResponseDto weatherResponseDto = new WeatherApiResponseDto(
                "London",
                new Coord(BigDecimal.valueOf(1), BigDecimal.valueOf(1)),
                List.of(new Weather("Clouds")),
                new Main(1, 2, 3)
        );
        List<WeatherApiResponseDto> expectedAnswer = List.of(weatherResponseDto);

        Mockito.doReturn(weatherResponseDto).when(openWeatherApiClientMock).getWeatherByCoordinates(BigDecimal.valueOf(1), BigDecimal.valueOf(1));
        Mockito.doReturn(locationList).when(locationRepositoryMock).findAllByUserId(Mockito.anyInt());

        List<WeatherApiResponseDto> actualAnswer = locationService.findLocationsByUserId(1);

        Assert.notNull(actualAnswer, "actual answer is null");
        assertEquals(expectedAnswer.size(), actualAnswer.size());
        assertEquals(expectedAnswer.getFirst().getName(), actualAnswer.getFirst().getName());
        assertEquals(expectedAnswer.getFirst().getWeather().getFirst().description(), actualAnswer.getFirst().getWeather().getFirst().description());
    }

    @Test
    void getWeatherByLocation_Success() {
        LocationDto locationResponseDto = new LocationDto(
                "London",
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(1)
        );
        List<LocationDto> expectedAnswer = List.of(locationResponseDto);

        Mockito.doReturn(expectedAnswer).when(openWeatherApiClientMock).getWeatherByLocation(Mockito.anyString());

        List<LocationDto> actualAnswer = locationService.findLocationsByCityName("London");

        Assert.notNull(actualAnswer, "actual answer is null");
        assertEquals(expectedAnswer.size(), actualAnswer.size());
        assertEquals(expectedAnswer.getFirst().name(), actualAnswer.getFirst().name());
    }

    @Test
    void getWeatherByEmptyLocation_ShouldThrowException() {
        LocationDto locationResponseDto = new LocationDto(
                "London",
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(1)
        );

        Mockito.doThrow(new RuntimeException("Ошибка: некорректный запрос")).when(openWeatherApiClientMock).getWeatherByLocation("");
        assertThrows(
                Exception.class,
                () -> locationService.findLocationsByCityName("")
        );
    }
}
