package org.example.DTO.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class WeatherApiDto {
    private Coord coord;
    private String name;
    private Main main;
    private List<Weather> weather;
    private Wind wind;
    private Clouds clouds;
    private Sys sys;

    @Data
    public static class Main {
        private double temp;

        @JsonProperty("feels_like")
        private double feelsLike;

        @JsonProperty("temp_min")
        private double tempMin;

        @JsonProperty("temp_max")
        private double tempMax;

        private int pressure;
        private int humidity;
    }

    @Data
    public static class Coord {
        @JsonProperty("lon")
        private BigDecimal longitude;
        @JsonProperty("lat")
        private BigDecimal latitude;
    }

    @Data
    public static class Weather {
        private String main;
        private String description;
        private String icon;
    }

    @Data
    public static class Wind {
        private double speed;
        private int deg;
    }

    @Data
    public static class Clouds {
        private int all;
    }

    @Data
    public static class Sys {
        private String country;
        private long sunrise;
        private long sunset;
    }
}
