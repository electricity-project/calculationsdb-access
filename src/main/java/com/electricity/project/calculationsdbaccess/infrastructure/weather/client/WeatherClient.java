package com.electricity.project.calculationsdbaccess.infrastructure.weather.client;


import com.electricity.project.calculationsdbaccess.api.weather.WeatherDTO;
import com.electricity.project.calculationsdbaccess.infrastructure.weather.entity.Coordinates;
import com.electricity.project.calculationsdbaccess.infrastructure.weather.entity.WeatherResponseAbstract;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

public interface WeatherClient {
    WeatherResponseAbstract getRealTimeWeather(@NonNull String apiKey, @NonNull Coordinates coordinates);
    WeatherDTO getForecastWeather(@NonNull String apiKey, @NonNull Coordinates coordinates, @NonNull LocalDate date);
}
