package com.electricity.project.calculationsdbaccess.api.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value
@Value.Immutable
@Value.Style
@JsonSerialize(as = ImmutableWeatherApiKeyDTO.class)
@JsonDeserialize(as = ImmutableWeatherApiKeyDTO.class)
public interface WeatherApiKeyDTO {

    static ImmutableWeatherApiKeyDTO.Builder builder() {
        return ImmutableWeatherApiKeyDTO.builder();
    }

    @JsonProperty(value = "weatherApiKey", required = true)
    String getWeatherApiKey();
}
