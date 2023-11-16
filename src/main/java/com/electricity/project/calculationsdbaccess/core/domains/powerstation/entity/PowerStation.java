package com.electricity.project.calculationsdbaccess.core.domains.powerstation.entity;

import com.electricity.project.calculationsdbaccess.api.powerstation.PowerStationState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PowerStation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 39, unique = true, nullable = false)
    private String ipv6Address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PowerStationState state;

    @Column(nullable = false)
    private LocalDateTime creationTime;

    @Column(nullable = false)
    private double maxPower;

}
