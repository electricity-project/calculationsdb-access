package com.electricity.project.calculationsdbaccess.core.domains.weather.control;

import com.electricity.project.calculationsdbaccess.core.domains.weather.entity.WeatherApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherApiKeyRepository extends JpaRepository<WeatherApiKey, Integer> {
    Optional<WeatherApiKey> findTopByOrderByChangeDateDesc();
}
