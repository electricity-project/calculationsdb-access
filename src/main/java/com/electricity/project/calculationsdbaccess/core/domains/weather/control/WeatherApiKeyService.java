package com.electricity.project.calculationsdbaccess.core.domains.weather.control;

import com.electricity.project.calculationsdbaccess.core.domains.weather.control.exception.InvalidWeatherApiKey;
import com.electricity.project.calculationsdbaccess.core.domains.weather.control.exception.IsCurrentWeatherApiKey;
import com.electricity.project.calculationsdbaccess.core.domains.weather.control.exception.NoWeatherApiKeyFound;
import com.electricity.project.calculationsdbaccess.core.domains.weather.entity.WeatherApiKey;
import com.electricity.project.calculationsdbaccess.infrastructure.weather.WeatherApiExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherApiKeyService {
    private final WeatherApiKeyRepository weatherRepository;
    private final WeatherApiExecutor weatherApiExecutor;

    public WeatherApiKey getWeatherApiKey() {
        return weatherRepository.findTopByOrderByChangeDateDesc()
                .orElseThrow(NoWeatherApiKeyFound::new);
    }

    public void addNewWeatherApiKey(WeatherApiKey weatherApiKey) {
        String newApiKey = weatherApiKey.getApiKey();

        weatherRepository.findTopByOrderByChangeDateDesc()
                .ifPresent(actualApiKey -> {
                    if (actualApiKey.getApiKey().equals(newApiKey)) {
                        throw new IsCurrentWeatherApiKey(newApiKey);
                    }
                });

        if (weatherApiExecutor.isWeatherApiKeyIncorrect(newApiKey)) {
            throw new InvalidWeatherApiKey(newApiKey);
        }

        weatherRepository.save(weatherApiKey);
    }
}
