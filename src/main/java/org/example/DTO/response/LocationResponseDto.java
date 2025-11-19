package org.example.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponseDto {
    private String name;
    private BigDecimal lat;
    private BigDecimal lon;
    private String country;
}
