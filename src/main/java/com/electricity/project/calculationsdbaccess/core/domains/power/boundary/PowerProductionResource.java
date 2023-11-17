package com.electricity.project.calculationsdbaccess.core.domains.power.boundary;


import com.electricity.project.calculationsdbaccess.api.production.PowerProductionDTO;
import com.electricity.project.calculationsdbaccess.core.domains.power.control.PowerProductionMapper;
import com.electricity.project.calculationsdbaccess.core.domains.power.control.PowerProductionService;
import com.electricity.project.calculationsdbaccess.core.domains.power.entity.PowerProduction;
import com.electricity.project.calculationsdbaccess.core.domains.powerstation.control.PowerStationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/power-production")
@RequiredArgsConstructor
@Slf4j
public class PowerProductionResource {

    private final PowerProductionService powerProductionService;
    private final PowerStationService powerStationService;

    @PostMapping("/list")
    public ResponseEntity<List<PowerProductionDTO>> addMultiplePowerProductionMessages(@RequestBody List<PowerProductionDTO> powerProductionDTOS) {
        List<PowerProduction> powerProduction = powerProductionDTOS.stream().map(PowerProductionMapper::mapToEntity).toList();
        List<PowerProductionDTO> savedDtoList = powerProductionService.savePowerProductionList(powerProduction)
                .stream()
                .map(PowerProductionMapper::mapToDTO)
                .toList();
        log.debug(String.join(",", savedDtoList.stream().map(Objects::toString).toArray(String[]::new)));
        log.info("Number of saved elements: {}", savedDtoList.size());
        return ResponseEntity.ok(savedDtoList);
    }

    @PostMapping
    public ResponseEntity<PowerProductionDTO> addPowerProductionMessage(@RequestBody PowerProductionDTO powerProductionDTO) {
        PowerProduction powerProduction = PowerProductionMapper.mapToEntity(powerProductionDTO);
        return ResponseEntity.ok(PowerProductionMapper.mapToDTO(powerProductionService.savePowerProduction(powerProduction)));
    }

}
