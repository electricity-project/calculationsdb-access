package com.electricity.project.calculationsdbaccess.api.solarpanel;

import com.electricity.project.calculationsdbaccess.api.powerstation.PowerStationDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@Value.Style
@JsonSerialize(as = ImmutableSolarPanelDTO.class)
@JsonDeserialize(as = ImmutableSolarPanelDTO.class)
public interface SolarPanelDTO extends PowerStationDTO {

    static ImmutableSolarPanelDTO.Builder builder() {
        return ImmutableSolarPanelDTO.builder();
    }

    @JsonProperty(value = "optimalTemperature", required = true)
    double getOptimalTemperature();

}
