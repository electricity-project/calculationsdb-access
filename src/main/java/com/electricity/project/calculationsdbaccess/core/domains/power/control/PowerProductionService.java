package com.electricity.project.calculationsdbaccess.core.domains.power.control;

import com.electricity.project.calculationsdbaccess.core.domains.power.entity.PowerProduction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PowerProductionService {

    private final PowerProductionRepository powerProductionRepository;

    public PowerProduction savePowerProduction(PowerProduction powerProduction) {
        return powerProductionRepository.save(powerProduction);
    }

    public List<PowerProduction> savePowerProductionList(List<PowerProduction> powerProductionList) {
        return powerProductionRepository.saveAll(powerProductionList);
    }

}
