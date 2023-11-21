package com.electricity.project.calculationsdbaccess.core.domains.aggregation.control;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PowerProductionAggregationService {
    private final PowerProductionAggregationRepository aggregationRepository;
}
