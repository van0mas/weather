package org.example.mapper;

import org.example.DTO.api.LocationApiDto;
import org.example.DTO.response.LocationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

@Mapper
public interface LocationResponseMapper {

    LocationResponseMapper INSTANCE = Mappers.getMapper(LocationResponseMapper.class);

    @Mapping(source = "latitude", target = "lat")
    @Mapping(source = "longitude", target = "lon")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "country", target = "country")
    LocationResponseDto from(LocationApiDto dto);

    default List<LocationResponseDto> from(LocationApiDto[] dto) {
        return Arrays.stream(dto)
                .map(this::from)
                .toList();
    }
}

