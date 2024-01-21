package com.electricity.project.calculationsdbaccess.core.domains.weather.boundary;

import com.electricity.project.calculationsdbaccess.api.error.ErrorDTO;
import com.electricity.project.calculationsdbaccess.api.weather.WeatherApiKeyDTO;
import com.electricity.project.calculationsdbaccess.core.domains.weather.control.WeatherApiKeyMapper;
import com.electricity.project.calculationsdbaccess.core.domains.weather.control.WeatherApiKeyService;
import com.electricity.project.calculationsdbaccess.core.domains.weather.control.exception.InvalidWeatherApiKey;
import com.electricity.project.calculationsdbaccess.core.domains.weather.control.exception.IsCurrentWeatherApiKey;
import com.electricity.project.calculationsdbaccess.core.domains.weather.control.exception.NoWeatherApiKeyFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/weather-api")
@RequiredArgsConstructor
public class WeatherApiResource {

    private final WeatherApiKeyService weatherApiKeyService;

    @GetMapping
    public ResponseEntity<WeatherApiKeyDTO> getActualWeatherApiKey() {
        return ResponseEntity.ok(WeatherApiKeyMapper.mapToDTO(weatherApiKeyService.getWeatherApiKey()));
    }

    @PostMapping
    public ResponseEntity<Object> addNewWeatherApiKey(@RequestBody WeatherApiKeyDTO weatherApiKeyDTO) {
        weatherApiKeyService.addNewWeatherApiKey(WeatherApiKeyMapper.mapToEntity(weatherApiKeyDTO));
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NoWeatherApiKeyFound.class)
    private ResponseEntity<ErrorDTO> handleNoWeatherApiKeyFound(NoWeatherApiKeyFound exception) {
        log.error("No weather api key in database", exception);
        return ResponseEntity.internalServerError().body(ErrorDTO.builder().error(exception.getMessage()).build());
    }

    @ExceptionHandler({InvalidWeatherApiKey.class, IsCurrentWeatherApiKey.class})
    private ResponseEntity<ErrorDTO> handleInvalidWeatherApiKey(RuntimeException exception) {
        log.error("Error occurred while adding new weather api key", exception);
        return ResponseEntity.badRequest().body(ErrorDTO.builder().error(exception.getMessage()).build());
    }
}
