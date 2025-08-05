package com.hellozai.weather.service;

import com.hellozai.weather.clients.OpenWeather;
import com.hellozai.weather.clients.WeatherStack;
import com.hellozai.weather.dto.response.OpenWeatherApiResponse;
import com.hellozai.weather.dto.response.WeatherResponse;
import com.hellozai.weather.dto.response.WeatherStackApiResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class WeatherServiceImpl implements IWeatherService {
    Logger log = LoggerFactory.getLogger(WeatherServiceImpl.class);

    OpenWeather openWeather;
    WeatherStack weatherStack;

    @Autowired
    public WeatherServiceImpl(OpenWeather openWeather, WeatherStack weatherStack) {
        this.openWeather = openWeather;
        this.weatherStack = weatherStack;
    }

    @Override
    @Cacheable("weather")
    @CircuitBreaker(name = "weatherstack", fallbackMethod = "fallbackService")
    public WeatherResponse getWeather(String city) {
        log.info("Retrieving weather...");
        WeatherStackApiResponse resp = weatherStack.getWeather(city);
        var newResp = new WeatherResponse();
        if (resp.getCurrent() != null) {
            newResp.setTemperatureDegrees(resp.getCurrent().getTemperature());
            newResp.setWindSpeed(resp.getCurrent().getWindSpeed());
        }
        return newResp;
    }
    @Cacheable("weather")
    public WeatherResponse fallbackService(String city, Exception t) {
        log.error("This fallback was triggered: {}", t.getMessage());
        OpenWeatherApiResponse resp = openWeather.getWeather(city);
        var newResp = new WeatherResponse();
        if (resp.getMain() != null) {
            newResp.setTemperatureDegrees(resp.getMain().getTemp());
            newResp.setWindSpeed(resp.getWind().getSpeed());
        }
        return newResp;
    }
}
