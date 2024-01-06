package com.electricity.project.calculationsdbaccess.infrastructure.weather.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherApiResponse extends WeatherResponseAbstract {

    @JsonProperty("last_updated")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Override
    public LocalDateTime getTime() {
        return time;
    }

    @JsonProperty("temp_c")
    @Override
    public double getCelsiusTemperature() {
        return celsiusTemperature;
    }

    @JsonProperty("wind_kph")
    @Override
    public double getKphWindSpeed() {
        return kphWindSpeed;
    }

    @JsonProperty("pressure_mb")
    @Override
    public double getMbPressure() {
        return mbPressure;
    }

    @JsonProperty("humidity")
    @Override
    public int getPercentageHumidity() {
        return percentageHumidity;
    }

    @JsonProperty(value = "cloud", access = JsonProperty.Access.READ_WRITE)
    @Override
    public int getPercentageCloudy() {
        return percentageCloudy;
    }
}
