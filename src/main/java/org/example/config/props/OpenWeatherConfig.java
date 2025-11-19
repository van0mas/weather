package org.example.config.props;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Getter
@Configuration
public class OpenWeatherConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Value("${weather.api.key}")
    private String key;

    @Value("${weather.api.base-url}")
    private String baseUrl;

    @Value("${weather.api.weather-path}")
    private String weatherPath;

    @Value("${weather.api.geocoding-path}")
    private String geocodingPath;

    @Value("${weather.api.location-count-limit}")
    private String locationCountLimit;
}
