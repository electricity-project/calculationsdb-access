package com.electricity.project.calculationsdbaccess.core.domains.weather.control;

import com.electricity.project.calculationsdbaccess.api.weather.ForecastDayWeatherDTO;
import com.electricity.project.calculationsdbaccess.api.weather.ForecastHourWeatherDTO;
import com.electricity.project.calculationsdbaccess.api.weather.WeatherDTO;
import com.electricity.project.calculationsdbaccess.infrastructure.weather.client.WeatherClient;
import com.electricity.project.calculationsdbaccess.infrastructure.weather.entity.Coordinates;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class WeatherForecastService {
    private static final Coordinates WROCLAW_COORDINATES = new Coordinates(17.038538, 51.107883);
    private final WeatherApiKeyService weatherApiKeyService;
    private final WeatherClient weatherClient;

    public List<ForecastHourWeatherDTO> getForecastFor(LocalDate date) {
        String weatherApiKey = weatherApiKeyService.getWeatherApiKey().getApiKey();
        WeatherDTO weatherDto = weatherClient.getForecastWeather(weatherApiKey, WROCLAW_COORDINATES, date);
        List<ForecastDayWeatherDTO> forecastDayWeather = weatherDto.getForecastWeather().getForecastDayWeather();
        return forecastDayWeather.isEmpty() ? emptyList() : forecastDayWeather.getFirst().getHoursWeather();
    }
}
