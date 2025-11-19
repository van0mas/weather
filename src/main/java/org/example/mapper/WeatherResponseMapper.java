package org.example.mapper;

import org.example.DTO.api.WeatherApiDto;
import org.example.DTO.response.WeatherResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WeatherResponseMapper {

    WeatherResponseMapper INSTANCE = Mappers.getMapper(WeatherResponseMapper.class);

    @Mapping(source = "main.temp", target = "temp")
    @Mapping(source = "main.feelsLike", target = "feelsLike")
    @Mapping(source = "main.humidity", target = "humidity")
    @Mapping(source = "sys.country", target = "country")
    @Mapping(source = "name", target = "name")
    @Mapping(expression = "java(getDescription(dto))", target = "description")
    @Mapping(expression = "java(getIcon(dto))", target = "icon")
    WeatherResponseDto from(WeatherApiDto dto);

    default String getDescription(WeatherApiDto dto) {
        if (dto.getWeather() != null && !dto.getWeather().isEmpty()) {
            return dto.getWeather().get(0).getDescription();
        }
        return "";
    }

    default String getIcon(WeatherApiDto dto) {
        if (dto.getWeather() != null && !dto.getWeather().isEmpty()) {
            return dto.getWeather().get(0).getIcon();
        }
        return "";
    }
}

