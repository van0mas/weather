package org.example.DTO.response;

import lombok.Data;

@Data
public class WeatherResponseDto {
    private Long id;
    private double temp;
    private String name;
    private String country;
    private double feelsLike;
    private String description;
    private int humidity;
    private String icon;
}
