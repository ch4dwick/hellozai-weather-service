package com.hellozai.weather.clients;

import com.hellozai.weather.config.ApiProperties;
import com.hellozai.weather.config.WeatherApiConfig;
import com.hellozai.weather.dto.response.OpenWeatherApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class OpenWeather implements RemoteWeatherApi {
    Logger log = LoggerFactory.getLogger(OpenWeather.class);

    static final String CLIENT_NAME = "openweather";
    RestClient restClient;
    WeatherApiConfig apiConfig;

    @Autowired
    OpenWeather(RestClient restClient, WeatherApiConfig apiConfig) {
        this.restClient = restClient;
        this.apiConfig = apiConfig;
    }

    @Override
    public OpenWeatherApiResponse getWeather(String city) {
        ApiProperties props = apiConfig.getConfig().get(CLIENT_NAME);
        // TODO: Country hard coded for now
        return restClient.get()
                .uri(props.getUrl() + "?appId={apiKey}&units=metric&q={city},{country}", props.getKey(), city, "AU")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(OpenWeatherApiResponse.class);
    }
}
