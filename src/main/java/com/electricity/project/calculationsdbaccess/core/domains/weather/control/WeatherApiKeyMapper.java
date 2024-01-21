package com.electricity.project.calculationsdbaccess.core.domains.weather.control;

import com.electricity.project.calculationsdbaccess.api.weather.WeatherApiKeyDTO;
import com.electricity.project.calculationsdbaccess.core.domains.weather.entity.WeatherApiKey;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WeatherApiKeyMapper {

    public static WeatherApiKeyDTO mapToDTO(WeatherApiKey weatherApiKey) {
        return WeatherApiKeyDTO.builder()
                .weatherApiKey(weatherApiKey.getApiKey())
                .build();
    }

    public static WeatherApiKey mapToEntity(WeatherApiKeyDTO weatherApiKeyDTO) {
        return WeatherApiKey.builder()
                .id(null)
                .changeDate(ZonedDateTime.now())
                .apiKey(weatherApiKeyDTO.getWeatherApiKey())
                .build();
    }
}
