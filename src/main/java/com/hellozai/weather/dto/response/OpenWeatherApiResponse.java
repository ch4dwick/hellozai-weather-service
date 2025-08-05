package com.hellozai.weather.dto.response;

public class OpenWeatherApiResponse implements WeatherRemoteApiResponse {
    Main main;
    Wind wind;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public static class Main {
        private float temp;

        public float getTemp() {
            return temp;
        }

        public void setTemp(float temp) {
            this.temp = temp;
        }
    }

    public static class Wind {
        private float speed;

        public float getSpeed() {
            return speed;
        }

        public void setSpeed(float speed) {
            this.speed = speed;
        }
    }
}
