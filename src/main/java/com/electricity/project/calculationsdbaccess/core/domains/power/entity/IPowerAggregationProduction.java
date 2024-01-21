package com.electricity.project.calculationsdbaccess.core.domains.power.entity;

import java.time.Instant;

public interface IPowerAggregationProduction {

    Long getAggregatedValue();

    Instant getAggregatedTimestamp();
}
