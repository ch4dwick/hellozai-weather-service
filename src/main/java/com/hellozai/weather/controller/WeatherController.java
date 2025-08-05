package com.hellozai.weather.controller;

import com.hellozai.weather.dto.response.WeatherResponse;
import com.hellozai.weather.service.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class WeatherController {
    private final IWeatherService wService;

    @Autowired
    public WeatherController(IWeatherService wService) {
        this.wService = wService;
    }

    @GetMapping("/weather")
    public WeatherResponse weather(@RequestParam(defaultValue = "melbourne") String city) {
        return wService.getWeather(city);
    }
}
