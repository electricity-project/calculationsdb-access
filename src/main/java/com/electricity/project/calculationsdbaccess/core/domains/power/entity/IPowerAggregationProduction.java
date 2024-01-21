package com.electricity.project.calculationsdbaccess.core.domains.power.entity;

import java.time.ZonedDateTime;

public interface IPowerAggregationProduction {

    Long getAggregatedValue();

    ZonedDateTime getAggregatedTimestamp();
}
