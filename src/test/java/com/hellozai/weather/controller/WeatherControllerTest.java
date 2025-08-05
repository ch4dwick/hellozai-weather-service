package com.hellozai.weather.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hellozai.weather.dto.response.WeatherResponse;
import com.hellozai.weather.service.IWeatherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherController.class)
class WeatherControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IWeatherService mockService;

    @Test
    @DisplayName("Call Weather API Success")
    // Reference: https://www.baeldung.com/spring-mockmvc-fetch-json
    void weather() throws Exception {
        var mockMelbourneResp = new WeatherResponse();
        var mockSydneyResp = new WeatherResponse();
        mockMelbourneResp.setWindSpeed(10f);
        mockMelbourneResp.setTemperatureDegrees(100f);
        mockSydneyResp.setWindSpeed(8f);
        mockSydneyResp.setTemperatureDegrees(21f);
        when(mockService.getWeather("melbourne")).thenReturn(mockMelbourneResp);
        when(mockService.getWeather("sydney")).thenReturn(mockSydneyResp);

        // Default value was "melbourne"
        MvcResult result = this.mockMvc.perform(get("/v1/weather?"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        String json = result.getResponse().getContentAsString();
        var weather = new ObjectMapper().readValue(json, WeatherResponse.class);

        assertNotNull(weather);
        assertEquals(10f, weather.getWindSpeed());
        assertEquals(100f, weather.getTemperatureDegrees());

        result = this.mockMvc.perform(get("/v1/weather?city=sydney"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        json = result.getResponse().getContentAsString();
        weather = new ObjectMapper().readValue(json, WeatherResponse.class);

        assertNotNull(weather);
        assertEquals(8f, weather.getWindSpeed());
        assertEquals(21f, weather.getTemperatureDegrees());
    }

}