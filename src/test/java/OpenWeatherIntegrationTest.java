import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.mock;

class OpenWeatherIntegrationTest {

    private WebClient webClientMock;

    @BeforeEach
    void setUp() {
        webClientMock = mock(WebClient.class);
    }

    @Test
    void getWeatherByLocation_ShouldReturnWeatherResponseDto() {
        String jsonResponse = """
                {
                  "coord": {
                    "lon": 30.2642,
                    "lat": 59.8944
                  },
                  "weather": [
                    {
                      "description": "mist"
                    }
                  ],
                  "main": {
                    "temp": -6.08,
                    "feels_like": -11.75,
                    "humidity": 93
                  },
                  "name": "Saint Petersburg"
                }
                """;

        WebClient.RequestHeadersUriSpec uriSpecMock = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec headersSpecMock = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpecMock = mock(WebClient.ResponseSpec.class);

        Mockito.when(webClientMock.get()).thenReturn(uriSpecMock);
        Mockito.when(uriSpecMock.uri(Mockito.anyString())).thenReturn(headersSpecMock);
        Mockito.when(headersSpecMock.retrieve()).thenReturn(responseSpecMock);
        Mockito.when(responseSpecMock.bodyToMono(String.class)).thenReturn(Mono.just(jsonResponse));
    }

}
