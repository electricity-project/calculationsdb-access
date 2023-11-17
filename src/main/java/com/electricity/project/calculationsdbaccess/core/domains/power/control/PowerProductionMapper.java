package com.electricity.project.calculationsdbaccess.core.domains.power.control;

import com.electricity.project.calculationsdbaccess.api.production.PowerProductionDTO;
import com.electricity.project.calculationsdbaccess.core.domains.power.entity.PowerProduction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PowerProductionMapper {

    public static PowerProductionDTO mapToDTO(PowerProduction powerProduction) {
        return PowerProductionDTO.builder()
                .id(powerProduction.getId())
                .ipv6Address(powerProduction.getIpv6())
                .state(powerProduction.getState())
                .producedPower(powerProduction.getProducedPower())
                .timestamp(powerProduction.getTimestamp())
                .build();
    }

    public static PowerProduction mapToEntity(PowerProductionDTO powerProductionDTO) {
        return PowerProduction.builder()
                .id(powerProductionDTO.getId().orElse(-1L))
                .ipv6(powerProductionDTO.getIpv6Address())
                .state(powerProductionDTO.getState())
                .producedPower(powerProductionDTO.getProducedPower())
                .timestamp(powerProductionDTO.getTimestamp())
                .build();
    }

}
