package com.electricity.project.calculationsdbaccess.core.domains.power.control;

import com.electricity.project.calculationsdbaccess.api.aggregation.AggregationPeriodType;
import com.electricity.project.calculationsdbaccess.core.domains.power.entity.PowerProduction;
import com.electricity.project.calculationsdbaccess.infrastructure.FilterDateParser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PowerProductionService {

    private final PowerProductionRepository powerProductionRepository;
    private final MissingPowerProductionFiller missingPowerProductionFiller;

    public PowerProduction savePowerProduction(PowerProduction powerProduction) {
        return powerProductionRepository.save(powerProduction);
    }

    @Transactional
    public List<PowerProduction> savePowerProductionList(List<PowerProduction> powerProductionList) {
        return powerProductionRepository.saveAll(powerProductionList);
    }

    public List<PowerProduction> getPowerProductionByIpv6(String ipv6, AggregationPeriodType periodType, Integer duration) {
        LocalDateTime filterDate = FilterDateParser.createFilterDate(periodType, duration);
        List<PowerProduction> resultList = powerProductionRepository.getByIpv6OrderByTimestampDesc(ipv6, filterDate);

        long durationInMinutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), filterDate);

        if (resultList.size() != durationInMinutes) {
            return missingPowerProductionFiller.fillMissingTimestamps(ipv6, durationInMinutes, resultList);
        }

        return resultList;
    }

}
