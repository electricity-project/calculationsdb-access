package com.electricity.project.calculationsdbaccess.api.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@Value.Style
@JsonSerialize(as = ImmutableCurrentWeatherDTO.class)
@JsonDeserialize(as = ImmutableCurrentWeatherDTO.class)
public interface CurrentWeatherDTO {
    static ImmutableCurrentWeatherDTO.Builder builder() {
        return ImmutableCurrentWeatherDTO.builder();
    }

    @JsonProperty(value = "wind_mps", required = true)
    double getMpSWindSpeed();
}
