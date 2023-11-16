package com.electricity.project.calculationsdbaccess.core.domains.powerstation.control;

import com.electricity.project.calculationsdbaccess.api.powerstation.PowerStationDTO;
import com.electricity.project.calculationsdbaccess.api.solarpanel.SolarPanelDTO;
import com.electricity.project.calculationsdbaccess.api.windturbine.WindTurbineDTO;
import com.electricity.project.calculationsdbaccess.core.domains.powerstation.control.exception.IncorrectPowerStationType;
import com.electricity.project.calculationsdbaccess.core.domains.powerstation.entity.PowerStation;
import com.electricity.project.calculationsdbaccess.core.domains.powerstation.entity.SolarPanel;
import com.electricity.project.calculationsdbaccess.core.domains.powerstation.entity.WindTurbine;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PowerStationMapper {

    public static PowerStation mapToEntity(PowerStationDTO powerStationDTO) {
        return switch (powerStationDTO) {
            case WindTurbineDTO windTurbineDTO -> mapToEntity(windTurbineDTO);
            case SolarPanelDTO solarPanelDTO -> mapToEntity(solarPanelDTO);
            default -> throw new IncorrectPowerStationType(powerStationDTO);
        };
    }

    public static WindTurbine mapToEntity(WindTurbineDTO windTurbineDTO) {
        return WindTurbine.builder()
                .id(windTurbineDTO.getId().orElse(-1L))
                .ipv6Address(windTurbineDTO.getIpv6Address())
                .creationTime(windTurbineDTO.getCreationTime())
                .state(windTurbineDTO.getState())
                .maxPower(windTurbineDTO.getMaxPower())
                .bladeLength(windTurbineDTO.getBladeLength())
                .build();
    }

    public static SolarPanel mapToEntity(SolarPanelDTO solarPanelDTO) {
        return SolarPanel.builder()
                .id(solarPanelDTO.getId().orElse(-1L))
                .ipv6Address(solarPanelDTO.getIpv6Address())
                .creationTime(solarPanelDTO.getCreationTime())
                .state(solarPanelDTO.getState())
                .maxPower(solarPanelDTO.getMaxPower())
                .optimalTemperature(solarPanelDTO.getOptimalTemperature())
                .build();
    }

    public static PowerStationDTO mapToDTO(PowerStation powerStation) {
        return switch (powerStation) {
            case WindTurbine windTurbine -> mapToDTO(windTurbine);
            case SolarPanel solarPanel -> mapToDTO(solarPanel);
            default -> throw new IncorrectPowerStationType(powerStation);
        };
    }

    public static WindTurbineDTO mapToDTO(WindTurbine windTurbine) {
        return WindTurbineDTO.builder()
                .id(windTurbine.getId())
                .ipv6Address(windTurbine.getIpv6Address())
                .creationTime(windTurbine.getCreationTime())
                .state(windTurbine.getState())
                .maxPower(windTurbine.getMaxPower())
                .bladeLength(windTurbine.getBladeLength())
                .build();
    }

    public static SolarPanelDTO mapToDTO(SolarPanel solarPanel) {
        return SolarPanelDTO.builder()
                .id(solarPanel.getId())
                .ipv6Address(solarPanel.getIpv6Address())
                .creationTime(solarPanel.getCreationTime())
                .state(solarPanel.getState())
                .maxPower(solarPanel.getMaxPower())
                .optimalTemperature(solarPanel.getOptimalTemperature())
                .build();
    }
}
