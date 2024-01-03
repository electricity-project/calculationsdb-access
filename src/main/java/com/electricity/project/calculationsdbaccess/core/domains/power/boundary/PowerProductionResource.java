package com.electricity.project.calculationsdbaccess.core.domains.power.boundary;


import com.electricity.project.calculationsdbaccess.api.aggregation.AggregationPeriodType;
import com.electricity.project.calculationsdbaccess.api.production.PowerProductionDTO;
import com.electricity.project.calculationsdbaccess.core.domains.power.control.PowerProductionMapper;
import com.electricity.project.calculationsdbaccess.core.domains.power.control.PowerProductionService;
import com.electricity.project.calculationsdbaccess.core.domains.power.entity.PowerProduction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/power-production")
@RequiredArgsConstructor
@Slf4j
public class PowerProductionResource {

    private final PowerProductionService powerProductionService;

    @PostMapping("/list")
    public ResponseEntity<List<PowerProductionDTO>> addMultiplePowerProductionMessages(@RequestBody List<PowerProductionDTO> powerProductionDTOS) {
        List<PowerProduction> powerProduction = powerProductionDTOS.stream().map(PowerProductionMapper::mapToEntity).toList();
        List<PowerProductionDTO> savedDtoList = powerProductionService.savePowerProductionList(powerProduction)
                .stream()
                .map(PowerProductionMapper::mapToDTO)
                .toList();
        log.debug("Received body: {}", String.join(",", savedDtoList.stream().map(Objects::toString).toArray(String[]::new)));
        return ResponseEntity.ok(savedDtoList);
    }

    @PostMapping
    public ResponseEntity<PowerProductionDTO> addPowerProductionMessage(@RequestBody PowerProductionDTO powerProductionDTO) {
        PowerProduction powerProduction = PowerProductionMapper.mapToEntity(powerProductionDTO);
        return ResponseEntity.ok(PowerProductionMapper.mapToDTO(powerProductionService.savePowerProduction(powerProduction)));
    }

    @GetMapping
    public ResponseEntity<List<PowerProductionDTO>> getPowerProductionForIpv6(
            @RequestParam String ipv6,
            @RequestParam AggregationPeriodType periodType,
            @RequestParam Integer duration
    ) {
        return ResponseEntity.ok(powerProductionService.getPowerProductionByIpv6(ipv6, periodType, duration)
                .stream()
                .map(PowerProductionMapper::mapToDTO)
                .toList()
        );
    }

    @GetMapping("/date")
    public ResponseEntity<List<PowerProductionDTO>> getPowerProductionForDate(@RequestParam LocalDateTime time) {
        return ResponseEntity.ok(powerProductionService.getPowerProductionForDate(time)
                .stream()
                .map(PowerProductionMapper::mapToDTO)
                .toList()
        );
    }

}
