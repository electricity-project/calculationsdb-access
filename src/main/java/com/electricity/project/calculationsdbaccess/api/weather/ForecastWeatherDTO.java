package com.electricity.project.calculationsdbaccess.api.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@Value.Style
@JsonSerialize(as = ImmutableForecastWeatherDTO.class)
@JsonDeserialize(as = ImmutableForecastWeatherDTO.class)
public interface ForecastWeatherDTO {
    static ImmutableForecastWeatherDTO.Builder builder() {
        return ImmutableForecastWeatherDTO.builder();
    }

    @JsonProperty(value = "forecastday", required = true)
    List<ForecastDayWeatherDTO> getForecastDayWeather();
}
