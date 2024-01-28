package com.electricity.project.calculationsdbaccess.core.domains.weather.control;

import com.electricity.project.calculationsdbaccess.api.weather.CurrentWeatherDTO;
import com.electricity.project.calculationsdbaccess.infrastructure.weather.entity.WeatherResponseAbstract;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrentWeatherMapper {
    public static CurrentWeatherDTO mapToDTO(WeatherResponseAbstract weatherResponseAbstract) {
        return CurrentWeatherDTO.builder()
                .mpSWindSpeed(weatherResponseAbstract.getKphWindSpeed() * 1000 / 3600)
                .build();
    }
}
