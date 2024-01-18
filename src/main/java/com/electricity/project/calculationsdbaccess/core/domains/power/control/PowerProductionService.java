package com.electricity.project.calculationsdbaccess.core.domains.power.control;

import com.electricity.project.calculationsdbaccess.api.aggregation.AggregationPeriodType;
import com.electricity.project.calculationsdbaccess.api.powerstation.PowerStationState;
import com.electricity.project.calculationsdbaccess.core.domains.power.entity.IPowerAggregationProduction;
import com.electricity.project.calculationsdbaccess.core.domains.power.entity.PowerProduction;
import com.electricity.project.calculationsdbaccess.infrastructure.FilterDateParser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

        List<PowerProduction> resultList = switch (periodType) {
            case MINUTE -> powerProductionRepository.getByIpv6OrderByTimestampDesc(ipv6, filterDate);
            case HOUR ->
                    mapToPowerProduction(powerProductionRepository.getByIpv6AndTimestampForHour(ipv6, filterDate), ipv6);
            case DAY ->
                    mapToPowerProduction(powerProductionRepository.getByIpv6AndTimestampForDay(ipv6, filterDate), ipv6);
        };

        if (resultList.size() != duration) {
            List<PowerProduction> powerProductions = missingPowerProductionFiller.fillMissingTimestamps(ipv6, periodType, duration, resultList);
            if (periodType.equals(AggregationPeriodType.MINUTE)) {
                return powerProductions.stream()
                        .map(powerProduction -> {
                            powerProduction.setTimestamp(powerProduction.getTimestamp().withSecond(0).withNano(0));
                            return powerProduction;
                        }).toList();
            }
            return powerProductions;
        }

        return resultList;
    }

    private List<PowerProduction> mapToPowerProduction(List<IPowerAggregationProduction> aggregationProductions, String ipv6) {
        return aggregationProductions.stream()
                .map(iPowerAggregationProduction -> PowerProduction.builder()
                        .ipv6(ipv6)
                        .timestamp(iPowerAggregationProduction.getAggregatedTimestamp())
                        .producedPower(iPowerAggregationProduction.getAggregatedValue())
                        .state(PowerStationState.WORKING)
                        .build()
                ).toList();
    }

    public List<PowerProduction> getPowerProductionForDate(LocalDateTime time) {
        LocalDateTime timeRoundedUpToMinutes = time.withSecond(0).withNano(0);
        return powerProductionRepository.getByTimestampGreaterThanEqualAndTimestampLessThan(
                timeRoundedUpToMinutes,
                timeRoundedUpToMinutes.plusMinutes(1));
    }
}
