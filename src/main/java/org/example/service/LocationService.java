package org.example.service;

import org.example.DTO.api.LocationApiDto;
import org.example.DTO.api.WeatherApiDto;
import org.example.DTO.request.LocationRequestDto;
import org.example.DTO.response.LocationResponseDto;
import org.example.DTO.response.WeatherResponseDto;
import org.example.mapper.LocationResponseMapper;
import org.example.mapper.WeatherResponseMapper;
import org.example.model.Location;
import org.example.model.User;
import org.example.repository.LocationRepository;
import org.example.service.open_weather.OpenWeatherService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LocationService {

    private final LocationRepository locationRepository;
    private final OpenWeatherService weatherClient;

    public List<LocationResponseDto> findLocation(String location) {
        LocationApiDto[] cities = weatherClient.findCities(location);
        if (cities == null || cities.length == 0) {
            return Collections.emptyList();
        }
        return LocationResponseMapper.INSTANCE.from(cities);
    }

    public void addLocation(User user, LocationRequestDto dto) {
        WeatherApiDto weather = weatherClient.getWeather(dto.getLat(), dto.getLon());
        if (weather != null && !weather.getName().isEmpty()) {
            weather.setName(dto.getName());
            locationRepository.save(new Location(weather, user));
        }
    }

    public List<WeatherResponseDto> getLocationsWeather(User user) {
        return locationRepository.getLocationsByUser(user).stream()
                .map(location -> {
                    WeatherApiDto weather = weatherClient.getWeather(
                            location.getLatitude(),
                            location.getLongitude()
                    );
                    if (weather == null) {
                        weather = new WeatherApiDto();
                    }
                    WeatherResponseDto dto = WeatherResponseMapper.INSTANCE.from(weather);
                    dto.setId(location.getId());
                    dto.setName(location.getName());
                    return dto;
                })
                .sorted(Comparator.comparing(WeatherResponseDto::getId))
                .toList();
    }

    public void deleteLocation(Long id, Long userId) { locationRepository.deleteByIdAndUserId(id, userId); }
}
