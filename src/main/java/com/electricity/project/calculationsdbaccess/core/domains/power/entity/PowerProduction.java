package com.electricity.project.calculationsdbaccess.core.domains.power.entity;


import com.electricity.project.calculationsdbaccess.api.powerstation.PowerStationState;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLInsert;
import org.hibernate.type.SqlTypes;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLInsert(sql = "INSERT INTO power_production (power_station_ipv6, produced_power, state, timestamp, id) VALUES (?, ?, ? , ?, ?)",
        check = ResultCheckStyle.NONE
)
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
    private ZonedDateTime timestamp;

}
