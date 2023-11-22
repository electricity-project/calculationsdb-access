package com.electricity.project.calculationsdbaccess.core.domains.aggregation.control.repository;

import com.electricity.project.calculationsdbaccess.api.aggregation.AggregationPeriodType;
import com.electricity.project.calculationsdbaccess.core.domains.aggregation.entity.PowerProductionAggregation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class RepositoryBean {

    @Bean
    Map<AggregationPeriodType, PowerProductionAggregationRepository<? extends PowerProductionAggregation>> getAggregationMap(
            List<PowerProductionAggregationRepository<? extends PowerProductionAggregation>> powerProductionRepositories
    ) {
        return powerProductionRepositories.stream()
                .filter(repository -> repository.getAggregationEnum() != null)
                .collect(Collectors.toMap(AggregationEnumBean::getAggregationEnum, value -> value));
    }

}
