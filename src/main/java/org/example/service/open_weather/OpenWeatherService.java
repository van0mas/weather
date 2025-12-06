package org.example.service.open_weather;

import org.example.DTO.api.LocationApiDto;
import org.example.DTO.api.WeatherApiDto;
import org.example.config.props.OpenWeatherConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class OpenWeatherService {

    private final OpenWeatherConfig config;
    private final RestTemplate restTemplate;

    public LocationApiDto[] findCities(String location) {
        String encodedLocation = URLEncoder.encode(location, StandardCharsets.UTF_8);
        String url = String.format("%s%s?q=%s&appid=%s&limit=%s",
                config.getBaseUrl(),
                config.getGeocodingPath(),
                encodedLocation,
                config.getKey(),
                config.getLocationCountLimit());

        return restTemplate.getForObject(url, LocationApiDto[].class);
    }

    public WeatherApiDto getWeather(BigDecimal lat, BigDecimal lon) {
        String url = String.format("%s%s?lat=%s&lon=%s&appid=%s&units=metric",
                config.getBaseUrl(),
                config.getWeatherPath(),
                lat, lon,
                config.getKey());

        return restTemplate.getForObject(url, WeatherApiDto.class);
    }
}
