package com.hellozai.weather.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response Object from this API
 */
public class WeatherResponse {
    @JsonProperty("wind_speed")
    private float windSpeed;
    @JsonProperty("temperature_degrees")
    private float temperatureDegrees;

    public float getWindSpeed() {
        return windSpeed;
    }
    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }
    public float getTemperatureDegrees() {
        return temperatureDegrees;
    }
    public void setTemperatureDegrees(float temperatureDegrees) {
        this.temperatureDegrees = temperatureDegrees;
    }
}