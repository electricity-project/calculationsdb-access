package com.electricity.project.calculationsdbaccess.core.domains.aggregation.boundary;

import com.electricity.project.calculationsdbaccess.api.aggregation.AggregationPeriodType;
import com.electricity.project.calculationsdbaccess.api.aggregation.PowerProductionAggregationDTO;
import com.electricity.project.calculationsdbaccess.core.domains.aggregation.control.PowerProductionAggregationMapper;
import com.electricity.project.calculationsdbaccess.core.domains.aggregation.control.PowerProductionAggregationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/aggregated-power-production")
@RequiredArgsConstructor
public class PowerProductionAggregationResource {

    private final PowerProductionAggregationService powerProductionAggregationService;

    @GetMapping
    public ResponseEntity<List<PowerProductionAggregationDTO>> getAggregatedPowerProduction(
            @RequestParam AggregationPeriodType periodType,
            @RequestParam Integer duration
    ) {
        return ResponseEntity.ok(
                powerProductionAggregationService.getLastRecords(periodType, duration)
                        .stream()
                        .map(PowerProductionAggregationMapper::mapToDTO)
                        .toList()
        );
    }
}
