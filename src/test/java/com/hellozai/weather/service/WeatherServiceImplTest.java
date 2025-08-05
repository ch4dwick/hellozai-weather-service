package com.hellozai.weather.service;

import com.hellozai.weather.clients.OpenWeather;
import com.hellozai.weather.clients.WeatherStack;
import com.hellozai.weather.dto.response.OpenWeatherApiResponse;
import com.hellozai.weather.dto.response.WeatherResponse;
import com.hellozai.weather.dto.response.WeatherStackApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("WeatherServiceTest Test")
class WeatherServiceImplTest {
    @MockitoBean
    WeatherStack mockWwStack;

    @MockitoBean
    OpenWeather mockOwStack;

    @Autowired
    IWeatherService service;

    @Test
    @DisplayName("Get Weather Success")
    void getWeather() {
        final var testCity = "melbourne";
        var mockResp = new WeatherStackApiResponse();
        mockResp.setCurrent(new WeatherStackApiResponse.Current());
        mockResp.getCurrent().setTemperature(88f);
        mockResp.getCurrent().setWindSpeed(10f);
        when(mockWwStack.getWeather(testCity)).thenReturn(mockResp);

        WeatherResponse actualResp = service.getWeather(testCity);
        assertEquals(10f, actualResp.getWindSpeed());
        assertEquals(88f, actualResp.getTemperatureDegrees());
    }

    @Test
    @DisplayName("Get Weather Fallback")
    void getWeatherFallback() {
        var mockResp = new OpenWeatherApiResponse();
        mockResp.setMain(new OpenWeatherApiResponse.Main());
        mockResp.getMain().setTemp(88f);
        mockResp.setWind(new OpenWeatherApiResponse.Wind());
        mockResp.getWind().setSpeed(10f);
        when(mockWwStack.getWeather("melbroune")).thenThrow(HttpClientErrorException.class);

        WeatherResponse actualResp = service.getWeather("melbourne");
        assertEquals(10f, actualResp.getWindSpeed());
        assertEquals(88f, actualResp.getTemperatureDegrees());
    }

    @Test
    @DisplayName("Test Cache")
    void testCache() throws InterruptedException {
        final var testCity = "melbourne";
        var mockResp = new WeatherStackApiResponse();
        mockResp.setCurrent(new WeatherStackApiResponse.Current());
        mockResp.getCurrent().setTemperature(88f);
        mockResp.getCurrent().setWindSpeed(10f);

        when(mockWwStack.getWeather(testCity)).thenReturn(mockResp);
        WeatherResponse actualResp = service.getWeather("melbourne");
        assertEquals(10f, actualResp.getWindSpeed());
        assertEquals(88f, actualResp.getTemperatureDegrees());

        // Run an unused value because of cache
        mockResp = new WeatherStackApiResponse();
        mockResp.setCurrent(new WeatherStackApiResponse.Current());
        mockResp.getCurrent().setTemperature(100f);
        mockResp.getCurrent().setWindSpeed(21f);
        when(mockWwStack.getWeather(testCity)).thenReturn(mockResp);
        actualResp = service.getWeather("melbourne");
        Thread.sleep(2000);

        assertEquals(10f, actualResp.getWindSpeed());
        assertEquals(88f, actualResp.getTemperatureDegrees());

        Thread.sleep(1000);
        actualResp = service.getWeather("melbourne");
        assertEquals(21f, actualResp.getWindSpeed());
        assertEquals(100f, actualResp.getTemperatureDegrees());
    }

}