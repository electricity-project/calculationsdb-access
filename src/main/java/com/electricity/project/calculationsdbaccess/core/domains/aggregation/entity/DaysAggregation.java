package com.electricity.project.calculationsdbaccess.core.domains.aggregation.entity;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@SuperBuilder
public class DaysAggregation extends PowerProductionAggregation {
}
