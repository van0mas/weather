package org.example.DTO.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SessionResponseDto {
    private UUID sessionId;
}
