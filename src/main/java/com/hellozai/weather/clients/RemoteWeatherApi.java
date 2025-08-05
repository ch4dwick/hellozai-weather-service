package com.hellozai.weather.clients;

import com.hellozai.weather.dto.response.WeatherRemoteApiResponse;

/**
 * Client object to make Remote API calls
 */
public interface RemoteWeatherApi {
    public WeatherRemoteApiResponse getWeather(String city);
}
