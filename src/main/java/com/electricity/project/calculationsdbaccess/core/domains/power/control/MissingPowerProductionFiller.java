package com.electricity.project.calculationsdbaccess.core.domains.power.control;

import com.electricity.project.calculationsdbaccess.core.domains.power.entity.PowerProduction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Component
public class MissingPowerProductionFiller {

    public List<PowerProduction> fillMissingTimestamps(String ipv6, long duration, List<PowerProduction> powerProductions) {
        List<PowerProduction> resultList = fillMissingValuesBetween(ipv6, powerProductions);

        if (resultList.size() != duration) {
            resultList = fillMissingValuesBefore(ipv6, duration, resultList);
        }

        if (resultList.size() != duration) {
            return fillMissingValuesAfter(ipv6, duration, resultList);
        }

        return resultList;
    }

    private List<PowerProduction> fillMissingValuesBetween(String ipv6, List<PowerProduction> powerProductions) {
        List<PowerProduction> resultList = new LinkedList<>();
        int i = 0;

        LocalDateTime lastDateInProductionAggregation = powerProductions.getLast().getTimestamp();
        for (LocalDateTime timestamp = powerProductions.getFirst().getTimestamp();
             timestamp.isAfter(lastDateInProductionAggregation) || timestamp.isEqual(lastDateInProductionAggregation);
             timestamp = timestamp.minusMinutes(1)) {

            PowerProduction powerProduction = powerProductions.get(i);
            if (!powerProduction.getTimestamp().equals(timestamp)) {
                resultList.add(buildEmptyPowerProduction(ipv6, timestamp));
            } else {
                resultList.add(powerProduction);
                i++;
            }
        }
        return resultList;
    }

    private List<PowerProduction> fillMissingValuesBefore(String ipv6, long duration, List<PowerProduction> powerProductions) {
        LinkedList<PowerProduction> resultList = new LinkedList<>();
        LocalDateTime firstTimestampInResultList = powerProductions.getFirst().getTimestamp();

        for (LocalDateTime timestamp = LocalDateTime.now().withSecond(0).withNano(0);
             timestamp.isAfter(firstTimestampInResultList);
             timestamp = timestamp.minusMinutes(1)) {
            resultList.add(buildEmptyPowerProduction(ipv6, timestamp));
        }
        resultList.addAll(powerProductions);

        if (resultList.size() > duration) {
            for (int i = 0; i < resultList.size() - duration; i++) {
                resultList.removeLast();
            }
        }

        return resultList;
    }

    private List<PowerProduction> fillMissingValuesAfter(String ipv6, long duration, List<PowerProduction> powerProductions) {
        long missingValues = duration - powerProductions.size();
        LocalDateTime lastDate = powerProductions.getLast().getTimestamp();

        for (long i = 0; i < missingValues; i++) {
            lastDate = lastDate.minusMinutes(1);
            powerProductions.add(buildEmptyPowerProduction(ipv6, lastDate));
        }

        return powerProductions;
    }

    private PowerProduction buildEmptyPowerProduction(String ipv6, LocalDateTime timestamp) {
        return PowerProduction.builder()
                .state(null)
                .producedPower(null)
                .ipv6(ipv6)
                .timestamp(timestamp)
                .build();

    }

}
