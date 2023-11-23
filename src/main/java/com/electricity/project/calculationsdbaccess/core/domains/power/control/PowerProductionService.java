package com.electricity.project.calculationsdbaccess.core.domains.power.control;

import com.electricity.project.calculationsdbaccess.core.domains.power.entity.PowerProduction;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PowerProductionService {

    private final PowerProductionRepository powerProductionRepository;

    public PowerProduction savePowerProduction(PowerProduction powerProduction) {
        return powerProductionRepository.save(powerProduction);
    }

    @Transactional
    public List<PowerProduction> savePowerProductionList(List<PowerProduction> powerProductionList) {
        return powerProductionRepository.saveAll(powerProductionList);
    }

    public List<PowerProduction> getPowerProductionByIpv6(String ipv6, Pageable pageable){
        return powerProductionRepository.getByIpv6OrderByTimestampDesc(ipv6, pageable);
    }

}
