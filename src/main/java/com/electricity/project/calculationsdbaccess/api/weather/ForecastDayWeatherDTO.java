package com.electricity.project.calculationsdbaccess.api.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@Value.Style
@JsonSerialize(as = ImmutableForecastDayWeatherDTO.class)
@JsonDeserialize(as = ImmutableForecastDayWeatherDTO.class)
public interface ForecastDayWeatherDTO {
    static ImmutableForecastDayWeatherDTO.Builder builder() {
        return ImmutableForecastDayWeatherDTO.builder();
    }

    @JsonProperty(value = "hour", required = true)
    List<ForecastHourWeatherDTO> getHoursWeather();
}
