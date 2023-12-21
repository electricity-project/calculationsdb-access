package com.electricity.project.calculationsdbaccess.core.domains.aggregation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@NoArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class PowerProductionAggregation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    protected Long id;

    @Column(nullable = false)
    protected Long aggregationValue;

    @Column(nullable = false)
    protected Long aggregatedPowerStations;

    @Column(nullable = false)
    protected LocalDateTime timestamp;

}
