package com.electricity.project.calculationsdbaccess.core.domains.power.entity;

import java.time.LocalDateTime;

public interface IPowerAggregationProduction {

    Long getAggregatedValue();

    LocalDateTime getAggregatedTimestamp();
}
