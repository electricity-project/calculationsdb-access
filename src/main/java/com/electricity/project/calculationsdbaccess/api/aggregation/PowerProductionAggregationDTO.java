package com.electricity.project.calculationsdbaccess.api.aggregation;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.time.LocalDateTime;
import java.util.Optional;

@Value.Immutable
@Value.Style
@JsonSerialize(as = ImmutablePowerProductionAggregationDTO.class)
@JsonDeserialize(as = ImmutablePowerProductionAggregationDTO.class)

public interface PowerProductionAggregationDTO {

    static ImmutablePowerProductionAggregationDTO.Builder builder() {
        return ImmutablePowerProductionAggregationDTO.builder();
    }

    @JsonProperty(value = "aggregatedValue")
    Optional<Long> getAggregationValue();

    @JsonProperty(value = "powerStations")
    Optional<Long> getPowerStations();

    @JsonProperty(value = "timestamp")
    LocalDateTime getTimestamp();

}
