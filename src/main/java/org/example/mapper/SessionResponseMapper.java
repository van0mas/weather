package org.example.mapper;

import org.example.DTO.response.SessionResponseDto;
import org.example.model.Session;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SessionResponseMapper {

    SessionResponseMapper INSTANCE = Mappers.getMapper(SessionResponseMapper.class);

    SessionResponseDto from(Session session);
}
