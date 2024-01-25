package com.electricity.project.calculationsdbaccess.api.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@Value.Style
@JsonSerialize(as = ImmutableWeatherDTO.class)
@JsonDeserialize(as = ImmutableWeatherDTO.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface WeatherDTO {
    static ImmutableWeatherDTO.Builder builder() {
        return ImmutableWeatherDTO.builder();
    }

    @JsonProperty(value = "forecast", required = true)
    ForecastWeatherDTO getForecastWeather();
}
