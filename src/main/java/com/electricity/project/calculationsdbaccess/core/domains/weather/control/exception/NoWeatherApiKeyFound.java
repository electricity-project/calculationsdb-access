package com.electricity.project.calculationsdbaccess.core.domains.weather.control.exception;

public class NoWeatherApiKeyFound extends RuntimeException {

    public NoWeatherApiKeyFound() {
        super("No weather api key in database");
    }
}
