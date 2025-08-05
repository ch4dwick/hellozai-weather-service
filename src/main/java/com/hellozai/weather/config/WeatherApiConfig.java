package com.hellozai.weather.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Reference: https://www.baeldung.com/configuration-properties-in-spring-boot
 */
@Configuration // This one seems to make the unit tests work
@ConfigurationProperties("api")
public class WeatherApiConfig {
    private Map<String, ApiProperties> config;

    public Map<String, ApiProperties> getConfig() {
        return config;
    }

    public void setConfig(Map<String, ApiProperties> config) {
        this.config = config;
    }
}
