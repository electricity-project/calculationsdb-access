package com.electricity.project.calculationsdbaccess.core.domains.weather.control.exception;

public class InvalidWeatherApiKey extends RuntimeException {

    public InvalidWeatherApiKey(String apiKey) {
        super("Cannot create connection for api key: " + apiKey);
    }
}
