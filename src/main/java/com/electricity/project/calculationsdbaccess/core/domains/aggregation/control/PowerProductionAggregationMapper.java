package com.electricity.project.calculationsdbaccess.core.domains.aggregation.control;

import com.electricity.project.calculationsdbaccess.api.aggregation.PowerProductionAggregationDTO;
import com.electricity.project.calculationsdbaccess.core.domains.aggregation.entity.PowerProductionAggregation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PowerProductionAggregationMapper {

    public static PowerProductionAggregationDTO mapToDTO(PowerProductionAggregation powerProductionAggregation) {
        return PowerProductionAggregationDTO.builder()
                .aggregationValue(powerProductionAggregation.getAggregationValue())
                .powerStations(powerProductionAggregation.getAggregatedPowerStations())
                .timestamp(powerProductionAggregation.getTimestamp())
                .build();
    }
}
