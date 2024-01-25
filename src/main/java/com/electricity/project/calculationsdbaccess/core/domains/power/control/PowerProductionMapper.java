package com.electricity.project.calculationsdbaccess.core.domains.power.control;

import com.electricity.project.calculationsdbaccess.api.powerstation.PowerStationState;
import com.electricity.project.calculationsdbaccess.api.production.PowerProductionDTO;
import com.electricity.project.calculationsdbaccess.core.domains.power.entity.PowerProduction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PowerProductionMapper {

    public static PowerProductionDTO mapToDTO(PowerProduction powerProduction) {
        return PowerProductionDTO.builder()
                .id(Optional.ofNullable(powerProduction.getId()))
                .ipv6Address(powerProduction.getIpv6())
                .state(Optional.ofNullable(powerProduction.getState()))
                .producedPower(Optional.ofNullable(powerProduction.getProducedPower()))
                .timestamp(powerProduction.getTimestamp())
                .build();
    }

    public static PowerProduction mapToEntity(PowerProductionDTO powerProductionDTO) {
        return PowerProduction.builder()
                .id(powerProductionDTO.getId().orElse(-1L))
                .ipv6(powerProductionDTO.getIpv6Address())
                .state(powerProductionDTO.getState().orElse(PowerStationState.WORKING))
                .producedPower(powerProductionDTO.getProducedPower().orElse(0L))
                .timestamp(powerProductionDTO.getTimestamp())
                .build();
    }

}
