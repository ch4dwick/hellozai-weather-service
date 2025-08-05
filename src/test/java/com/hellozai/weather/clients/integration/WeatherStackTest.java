package com.hellozai.weather.clients.integration;

import com.hellozai.weather.clients.WeatherStack;
import com.hellozai.weather.dto.response.WeatherStackApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Weatherstack Integration test
 *
 * Caveat: Rate limits prevent this test from occasionally running completely
 */
@SpringBootTest
@Profile("integration")
@DisplayName("WeatherStack Test")
class WeatherStackTest {
    @Autowired
    WeatherStack wsClient;

    final String testCity = "melbourne";

    /**
     * Note: Due to rate limits, the test for temp & wind speed are combined to reduce API calls.
     */
    @Test
    @DisplayName("Get Temp & Wind Speed")
    void getWeatherTemp() {
        WeatherStackApiResponse resp = wsClient.getWeather(testCity);

        assertNotNull(resp);
        assertNotNull(resp.getCurrent());
        assertThat(resp.getCurrent().getTemperature()).isGreaterThan(0);
        assertThat(resp.getCurrent().getWindSpeed()).isGreaterThan(0);

    }

    @Test
    @DisplayName("Connectivity with invalid")
    void getWeatherInvalid() {
        assertThrows(HttpClientErrorException.class, () -> wsClient.getWeather("invalid"));
    }

}