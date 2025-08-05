package com.hellozai.weather.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherStackApiResponse implements WeatherRemoteApiResponse {
    private Current current;

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public static class Current {
        private float temperature;
        private float windSpeed;

        public float getTemperature() {
            return temperature;
        }

        public void setTemperature(float temperature) {
            this.temperature = temperature;
        }
        @JsonProperty("wind_speed")
        public float getWindSpeed() {
            return windSpeed;
        }

        public void setWindSpeed(float windSpeed) {
            this.windSpeed = windSpeed;
        }
    }
}

