package com.hellozai.weather.clients;

import com.hellozai.weather.config.ApiProperties;
import com.hellozai.weather.config.WeatherApiConfig;
import com.hellozai.weather.dto.response.WeatherStackApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class WeatherStack implements RemoteWeatherApi {
    Logger log = LoggerFactory.getLogger(WeatherStack.class);
    static final String CLIENT_NAME = "weatherstack";
    RestClient restClient;
    WeatherApiConfig apiConfig;
    @Autowired
    WeatherStack(RestClient restClient, WeatherApiConfig apiConfig) {
        this.restClient = restClient;
        this.apiConfig = apiConfig;
    }

    @Override
//    @Cacheable("weather")
    public WeatherStackApiResponse getWeather(String city) {
        ApiProperties props = apiConfig.getConfig().get(CLIENT_NAME);
        return restClient.get()
                .uri(props.getUrl() + "?access_key={apiKey}&query={city}", props.getKey(), city)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(WeatherStackApiResponse.class);
    }
}
