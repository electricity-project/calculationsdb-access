package com.electricity.project.calculationsdbaccess.core.domains.weather.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(nullable = false)
    private String apiKey;

    @Column(nullable = false)
    private LocalDateTime changeDate;
}
