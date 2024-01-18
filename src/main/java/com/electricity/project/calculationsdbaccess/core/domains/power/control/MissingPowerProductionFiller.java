package com.electricity.project.calculationsdbaccess.core.domains.power.control;

import com.electricity.project.calculationsdbaccess.api.aggregation.AggregationPeriodType;
import com.electricity.project.calculationsdbaccess.core.domains.power.entity.PowerProduction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Component
public class MissingPowerProductionFiller {

    public List<PowerProduction> fillMissingTimestamps(String ipv6, AggregationPeriodType periodType,
                                                       int duration, List<PowerProduction> powerProductions) {
        if (powerProductions.isEmpty()) {
            return fillMissingValuesIfListIsEmpty(ipv6, duration);
        }

        List<PowerProduction> resultList = fillMissingValuesBetween(periodType, powerProductions, ipv6);

        if (resultList.size() != duration) {
            resultList = fillMissingValuesBefore(duration, periodType, resultList, ipv6);
        }

        if (resultList.size() != duration) {
            return fillMissingValuesAfter(ipv6, duration, periodType, resultList);
        }

        return resultList;
    }

    private List<PowerProduction> fillMissingValuesIfListIsEmpty(String ipv6, int duration) {
        List<PowerProduction> resultList = new LinkedList<>();
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < duration; i++) {
            resultList.add(buildEmptyPowerProduction(ipv6, now.minusMinutes(1)));
        }
        return resultList;
    }

    private List<PowerProduction> fillMissingValuesBetween(AggregationPeriodType periodType,
                                                           List<PowerProduction> powerProductions, String ipv6) {
        List<PowerProduction> resultList = new LinkedList<>();
        int i = 0;

        LocalDateTime lastDateInProductionAggregation = powerProductions.getLast().getTimestamp().withSecond(0).withNano(0);
        for (LocalDateTime timestamp = powerProductions.getFirst().getTimestamp().withSecond(0).withNano(0);
             timestamp.isAfter(lastDateInProductionAggregation) || timestamp.isEqual(lastDateInProductionAggregation);
             timestamp = parseTime(timestamp, periodType)) {

            PowerProduction powerProduction = powerProductions.get(i);
            if (!powerProduction.getTimestamp().withSecond(0).withNano(0).equals(timestamp)) {
                resultList.add(buildEmptyPowerProduction(ipv6, timestamp));
            } else {
                resultList.add(powerProduction);
                i++;
            }
        }
        return resultList;
    }

    private List<PowerProduction> fillMissingValuesBefore(int duration, AggregationPeriodType periodType,
                                                          List<PowerProduction> resultList, String ipv6) {
        LinkedList<PowerProduction> resultsBefore = new LinkedList<>();
        LocalDateTime firstTimestampInResultList = resultList.getFirst().getTimestamp();

        for (LocalDateTime timestamp = parseDateTimeNow(periodType);
             timestamp.isAfter(firstTimestampInResultList);
             timestamp = parseTime(timestamp, periodType)) {
            resultsBefore.add(buildEmptyPowerProduction(ipv6, timestamp));
        }
        resultsBefore.addAll(resultList);

        if (resultsBefore.size() > duration) {
            for (int i = 0; i < resultsBefore.size() - duration + 1; i++) {
                resultsBefore.removeLast();
            }
        }

        return resultsBefore;
    }

    private List<PowerProduction> fillMissingValuesAfter(String ipv6, int duration, AggregationPeriodType periodType,
                                                         List<PowerProduction> resultList) {
        int missingValues = duration - resultList.size();
        LocalDateTime lastDate = resultList.getLast().getTimestamp();

        for (int i = 0; i < missingValues; i++) {
            lastDate = parseTime(lastDate, periodType);
            resultList.add(buildEmptyPowerProduction(ipv6, lastDate));
        }

        return resultList;
    }

    private static LocalDateTime parseDateTimeNow(AggregationPeriodType periodType) {
        return switch (periodType) {
            case MINUTE -> LocalDateTime.now().withSecond(0).withNano(0);
            case HOUR -> LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
            case DAY -> LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        };
    }

    private static LocalDateTime parseTime(LocalDateTime timestamp, AggregationPeriodType periodType) {
        return switch (periodType) {
            case MINUTE -> timestamp.minusMinutes(1);
            case HOUR -> timestamp.minusHours(1);
            case DAY -> timestamp.minusDays(1);
        };
    }

    public static PowerProduction buildEmptyPowerProduction(String ipv6, LocalDateTime timestamp) {
        return PowerProduction.builder()
                .state(null)
                .producedPower(null)
                .ipv6(ipv6)
                .timestamp(timestamp)
                .build();

    }

}
