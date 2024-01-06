package com.electricity.project.calculationsdbaccess.infrastructure.weather;

import com.electricity.project.calculationsdbaccess.infrastructure.weather.client.WeatherClient;
import com.electricity.project.calculationsdbaccess.infrastructure.weather.entity.Coordinates;
import com.electricity.project.calculationsdbaccess.infrastructure.weather.entity.WeatherResponseAbstract;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class WeatherApiExecutor {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final WeatherClient weatherClient;

    public boolean isWeatherApiKeyIncorrect(String apiKey) {
        Future<WeatherResponseAbstract> weatherResponseFuture = executor.submit(
                () -> weatherClient.getRealTimeWeather(apiKey, new Coordinates(17.038538, 51.107883)));
        try {
            WeatherResponseAbstract weatherResponseAbstract = weatherResponseFuture.get(35, TimeUnit.SECONDS);
            return weatherResponseAbstract == null;
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.error("Getting realtime weather error", e);
            return true;
        }
    }
}
