package com.electricity.project.calculationsdbaccess.core.domains.weather.control.exception;

public class IsCurrentWeatherApiKey extends RuntimeException {
    public IsCurrentWeatherApiKey(String apiKey) {
        super("This is current weather api key: " + apiKey);
    }
}
