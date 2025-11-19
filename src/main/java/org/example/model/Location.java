package org.example.model;


import org.example.DTO.api.WeatherApiDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "locations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "latitude", "longitude"})
)
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, precision = 9, scale = 4)
    private BigDecimal latitude;

    @Column(nullable = false, precision = 9, scale = 4)
    private BigDecimal longitude;

    public Location(WeatherApiDto weather, User user) {
        this.name = weather.getName();
        this.latitude = weather.getCoord().getLatitude();
        this.longitude = weather.getCoord().getLongitude();
        this.user = user;
    }
}

