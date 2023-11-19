package com.electricity.project.calculationsdbaccess.core.domains.power.entity;


import com.electricity.project.calculationsdbaccess.api.powerstation.PowerStationState;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PowerProduction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    @JdbcTypeCode(SqlTypes.REAL)
    private Long producedPower;

    @Column(name = "power_station_ipv6", nullable = false)
    private String ipv6;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PowerStationState state;

    @Column(nullable = false)
    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    private LocalDateTime timestamp;

}
