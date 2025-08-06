package com.hellozai.weather.clients;

import com.hellozai.weather.clients.OpenWeather;
import com.hellozai.weather.dto.response.OpenWeatherApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * OpenWeather Integration test
 */
@SpringBootTest
@Profile("integration")
@DisplayName("OpenWeather Test")
class OpenWeatherIntegrationTest {
    @Autowired
    OpenWeather owClient;

    final String testCity = "melbourne";

    @Test
    @DisplayName("Get Temperature")
    void getWeatherTemp() {
        OpenWeatherApiResponse resp = owClient.getWeather(testCity);

        assertNotNull(resp);
        assertNotNull(resp.getMain());
        assertThat(resp.getMain().getTemp()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Get Wind Speed")
    void getWeatherWindSpeed() {
        OpenWeatherApiResponse resp = owClient.getWeather(testCity);

        assertNotNull(resp);
        assertNotNull(resp.getWind());
        assertThat(resp.getWind().getSpeed()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Connectivity with invalid")
    void getWeatherInvalid() {
        assertThrows(HttpClientErrorException.class, () -> owClient.getWeather("invalid"));
    }
}