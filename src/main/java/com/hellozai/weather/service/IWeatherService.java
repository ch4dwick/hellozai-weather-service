package com.hellozai.weather.service;

import com.hellozai.weather.dto.response.WeatherResponse;

public interface IWeatherService {
    public WeatherResponse getWeather(String city);
}
