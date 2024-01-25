package com.electricity.project.calculationsdbaccess.api.production;

import com.electricity.project.calculationsdbaccess.api.powerstation.PowerStationState;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.time.ZonedDateTime;
import java.util.Optional;

@Value.Immutable
@Value.Style
@JsonSerialize(as = ImmutablePowerProductionDTO.class)
@JsonDeserialize(as = ImmutablePowerProductionDTO.class)
public interface PowerProductionDTO {

    static ImmutablePowerProductionDTO.Builder builder() {
        return ImmutablePowerProductionDTO.builder();
    }

    @JsonProperty(value = "id")
    Optional<Long> getId();

    @JsonProperty(value = "ipv6Address", required = true)
    String getIpv6Address();

    @JsonProperty(value = "state", required = true)
    Optional<PowerStationState> getState();

    @JsonProperty(value = "power", required = true)
    Optional<Long> getProducedPower();

    @JsonProperty(value = "timestamp", required = true)
    ZonedDateTime getTimestamp();
}
