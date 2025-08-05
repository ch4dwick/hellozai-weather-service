package com.hellozai.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableCaching
public class HellozaiWeatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(HellozaiWeatherApplication.class, args);
	}

}
