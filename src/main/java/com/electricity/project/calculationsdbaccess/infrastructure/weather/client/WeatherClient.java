package com.electricity.project.calculationsdbaccess.infrastructure.weather.client;


import com.electricity.project.calculationsdbaccess.infrastructure.weather.entity.Coordinates;
import com.electricity.project.calculationsdbaccess.infrastructure.weather.entity.WeatherResponseAbstract;
import org.springframework.lang.NonNull;

public interface WeatherClient {
    WeatherResponseAbstract getRealTimeWeather(@NonNull String apiKey, @NonNull Coordinates coordinates);
}
