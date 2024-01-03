package com.electricity.project.calculationsdbaccess.core.domains.aggregation.control;

import com.electricity.project.calculationsdbaccess.api.aggregation.AggregationPeriodType;
import com.electricity.project.calculationsdbaccess.core.domains.aggregation.control.repository.PowerProductionAggregationRepository;
import com.electricity.project.calculationsdbaccess.core.domains.aggregation.entity.MinutesAggregation;
import com.electricity.project.calculationsdbaccess.core.domains.aggregation.entity.PowerProductionAggregation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@Slf4j
class PowerProductionAggregationServiceTest {

    private final MissingPowerProductionAggregationFiller missingPowerProductionAggregationFiller = new MissingPowerProductionAggregationFiller();
    private Map<AggregationPeriodType, PowerProductionAggregationRepository<? extends PowerProductionAggregation>> aggregationMap;
    private PowerProductionAggregationService powerProductionAggregationService;
    private LocalDateTime dateTime;
    private Integer duration;
    private List<PowerProductionAggregation> allPowerAggregation;

    @BeforeEach
    void setUp() {
        aggregationMap = new HashMap<>();
        dateTime = LocalDateTime.now();
        duration = 10;
        allPowerAggregation = createListWitPowerProduction(dateTime, duration);
    }

    @Test
    void getLastRecords_WhenMissingDataInMiddle_ShouldReturnFilledList() {
        List<PowerProductionAggregation> aggregationsWithMissingValues = new java.util.ArrayList<>(List.copyOf(allPowerAggregation));
        aggregationsWithMissingValues.remove(1);
        aggregationsWithMissingValues.remove(1);
        aggregationsWithMissingValues.remove(1);
        allPowerAggregation.set(1, builtEmptyAggregation(dateTime.minusMinutes(1)));
        allPowerAggregation.set(2, builtEmptyAggregation(dateTime.minusMinutes(2)));
        allPowerAggregation.set(3, builtEmptyAggregation(dateTime.minusMinutes(3)));

        PowerProductionAggregationRepository powerProductionRepositories = Mockito.mock(PowerProductionAggregationRepository.class);
        Mockito.when(powerProductionRepositories.findByOrderByTimestampAsc(any(LocalDateTime.class))).thenReturn(aggregationsWithMissingValues);

        aggregationMap.put(AggregationPeriodType.MINUTE, powerProductionRepositories);
        powerProductionAggregationService = new PowerProductionAggregationService(aggregationMap, missingPowerProductionAggregationFiller);


        List<PowerProductionAggregation> resultList = powerProductionAggregationService.getLastRecords(AggregationPeriodType.MINUTE, duration);

        assertEquals(allPowerAggregation.size(), resultList.size());
        for (int i = 0; i < resultList.size(); i++) {
            assertEquals(allPowerAggregation.get(i).getAggregatedPowerStations(), resultList.get(i).getAggregatedPowerStations());
            assertEquals(allPowerAggregation.get(i).getAggregationValue(), resultList.get(i).getAggregationValue());
            assertEquals(allPowerAggregation.get(i).getTimestamp(), resultList.get(i).getTimestamp());
        }
    }

    @Test
    void getLastRecords_WhenMissingDataBefore_ShouldReturnFilledList() {
        List<PowerProductionAggregation> aggregationsWithMissingValues = new java.util.ArrayList<>(List.copyOf(allPowerAggregation));
        aggregationsWithMissingValues.removeFirst();
        aggregationsWithMissingValues.removeFirst();
        aggregationsWithMissingValues.removeFirst();
        allPowerAggregation.set(0, builtEmptyAggregation(dateTime.minusMinutes(0)));
        allPowerAggregation.set(1, builtEmptyAggregation(dateTime.minusMinutes(1)));
        allPowerAggregation.set(2, builtEmptyAggregation(dateTime.minusMinutes(2)));

        PowerProductionAggregationRepository powerProductionRepositories = Mockito.mock(PowerProductionAggregationRepository.class);
        Mockito.when(powerProductionRepositories.findByOrderByTimestampAsc(any(LocalDateTime.class))).thenReturn(aggregationsWithMissingValues);

        aggregationMap.put(AggregationPeriodType.MINUTE, powerProductionRepositories);
        powerProductionAggregationService = new PowerProductionAggregationService(aggregationMap, missingPowerProductionAggregationFiller);

        List<PowerProductionAggregation> resultList = powerProductionAggregationService.getLastRecords(AggregationPeriodType.MINUTE, duration);

        assertEquals(allPowerAggregation.size(), resultList.size());
        for (int i = 0; i < resultList.size(); i++) {
            assertEquals(allPowerAggregation.get(i).getAggregatedPowerStations(), resultList.get(i).getAggregatedPowerStations());
            assertEquals(allPowerAggregation.get(i).getAggregationValue(), resultList.get(i).getAggregationValue());
        }
    }

    @Test
    void getLastRecords_WhenMissingDataAfter_ShouldReturnFilledList() {
        List<PowerProductionAggregation> aggregationsWithMissingValues = new java.util.ArrayList<>(List.copyOf(allPowerAggregation));
        aggregationsWithMissingValues.removeLast();
        aggregationsWithMissingValues.removeLast();
        aggregationsWithMissingValues.removeLast();
        allPowerAggregation.set(allPowerAggregation.size() - 1, builtEmptyAggregation(dateTime.minusMinutes(allPowerAggregation.size() - 1)));
        allPowerAggregation.set(allPowerAggregation.size() - 2, builtEmptyAggregation(dateTime.minusMinutes(allPowerAggregation.size() - 2)));
        allPowerAggregation.set(allPowerAggregation.size() - 3, builtEmptyAggregation(dateTime.minusMinutes(allPowerAggregation.size() - 3)));

        PowerProductionAggregationRepository powerProductionRepositories = Mockito.mock(PowerProductionAggregationRepository.class);
        Mockito.when(powerProductionRepositories.findByOrderByTimestampAsc(any(LocalDateTime.class))).thenReturn(aggregationsWithMissingValues);

        aggregationMap.put(AggregationPeriodType.MINUTE, powerProductionRepositories);
        powerProductionAggregationService = new PowerProductionAggregationService(aggregationMap, missingPowerProductionAggregationFiller);

        List<PowerProductionAggregation> resultList = powerProductionAggregationService.getLastRecords(AggregationPeriodType.MINUTE, duration);

        printLists(allPowerAggregation, resultList);

        assertEquals(allPowerAggregation.size(), resultList.size());
        for (int i = 0; i < resultList.size(); i++) {
            assertEquals(allPowerAggregation.get(i).getAggregatedPowerStations(), resultList.get(i).getAggregatedPowerStations());
            assertEquals(allPowerAggregation.get(i).getAggregationValue(), resultList.get(i).getAggregationValue());
            assertEquals(allPowerAggregation.get(i).getTimestamp(), resultList.get(i).getTimestamp());
        }
    }

    @Test
    void getLastRecords_WhenMissingDataBeforeAndInMiddle_ShouldReturnFilledList() {
        List<PowerProductionAggregation> aggregationsWithMissingValues = new java.util.ArrayList<>(List.copyOf(allPowerAggregation));
        aggregationsWithMissingValues.removeFirst();
        aggregationsWithMissingValues.removeFirst();
        aggregationsWithMissingValues.removeFirst();
        allPowerAggregation.set(0, builtEmptyAggregation(dateTime.minusMinutes(0)));
        allPowerAggregation.set(1, builtEmptyAggregation(dateTime.minusMinutes(1)));
        allPowerAggregation.set(2, builtEmptyAggregation(dateTime.minusMinutes(2)));

        aggregationsWithMissingValues.remove(2);
        aggregationsWithMissingValues.remove(2);
        aggregationsWithMissingValues.remove(2);
        allPowerAggregation.set(5, builtEmptyAggregation(dateTime.minusMinutes(5)));
        allPowerAggregation.set(6, builtEmptyAggregation(dateTime.minusMinutes(6)));
        allPowerAggregation.set(7, builtEmptyAggregation(dateTime.minusMinutes(7)));

        PowerProductionAggregationRepository powerProductionRepositories = Mockito.mock(PowerProductionAggregationRepository.class);
        Mockito.when(powerProductionRepositories.findByOrderByTimestampAsc(any(LocalDateTime.class))).thenReturn(aggregationsWithMissingValues);

        aggregationMap.put(AggregationPeriodType.MINUTE, powerProductionRepositories);
        powerProductionAggregationService = new PowerProductionAggregationService(aggregationMap, missingPowerProductionAggregationFiller);

        List<PowerProductionAggregation> resultList = powerProductionAggregationService.getLastRecords(AggregationPeriodType.MINUTE, duration);

        assertEquals(allPowerAggregation.size(), resultList.size());
        for (int i = 0; i < resultList.size(); i++) {
            assertEquals(allPowerAggregation.get(i).getAggregatedPowerStations(), resultList.get(i).getAggregatedPowerStations());
            assertEquals(allPowerAggregation.get(i).getAggregationValue(), resultList.get(i).getAggregationValue());
        }
    }

    @Test
    void getLastRecords_WhenMissingDataBeforeInMiddleAndAfter_ShouldReturnFilledList() {
        List<PowerProductionAggregation> aggregationsWithMissingValues = new java.util.ArrayList<>(List.copyOf(allPowerAggregation));
        aggregationsWithMissingValues.removeFirst();
        aggregationsWithMissingValues.removeFirst();
        allPowerAggregation.set(0, builtEmptyAggregation(dateTime.minusMinutes(0)));
        allPowerAggregation.set(1, builtEmptyAggregation(dateTime.minusMinutes(1)));

        aggregationsWithMissingValues.remove(2);
        aggregationsWithMissingValues.remove(2);
        allPowerAggregation.set(4, builtEmptyAggregation(dateTime.minusMinutes(4)));
        allPowerAggregation.set(5, builtEmptyAggregation(dateTime.minusMinutes(5)));

        aggregationsWithMissingValues.removeLast();
        aggregationsWithMissingValues.removeLast();
        allPowerAggregation.set(allPowerAggregation.size() - 1, builtEmptyAggregation(dateTime.minusMinutes(allPowerAggregation.size() - 1)));
        allPowerAggregation.set(allPowerAggregation.size() - 2, builtEmptyAggregation(dateTime.minusMinutes(allPowerAggregation.size() - 2)));

        PowerProductionAggregationRepository powerProductionRepositories = Mockito.mock(PowerProductionAggregationRepository.class);
        Mockito.when(powerProductionRepositories.findByOrderByTimestampAsc(any(LocalDateTime.class))).thenReturn(aggregationsWithMissingValues);

        aggregationMap.put(AggregationPeriodType.MINUTE, powerProductionRepositories);
        powerProductionAggregationService = new PowerProductionAggregationService(aggregationMap, missingPowerProductionAggregationFiller);

        List<PowerProductionAggregation> resultList = powerProductionAggregationService.getLastRecords(AggregationPeriodType.MINUTE, duration);

        assertEquals(allPowerAggregation.size(), resultList.size());
        for (int i = 0; i < resultList.size(); i++) {
            assertEquals(allPowerAggregation.get(i).getAggregatedPowerStations(), resultList.get(i).getAggregatedPowerStations());
            assertEquals(allPowerAggregation.get(i).getAggregationValue(), resultList.get(i).getAggregationValue());
        }
    }


    private static MinutesAggregation builtEmptyAggregation(LocalDateTime timestamp) {
        return MinutesAggregation.builder()
                .aggregationValue(null)
                .aggregatedPowerStations(null)
                .timestamp(timestamp).build();
    }

    private static List<PowerProductionAggregation> createListWitPowerProduction(LocalDateTime dateTime, Integer duration) {
        List<PowerProductionAggregation> resultList = new LinkedList<>();

        for (int i = 0; i < duration; i++) {
            resultList.add(MinutesAggregation.builder()
                    .aggregatedPowerStations(100L)
                    .aggregationValue(100L)
                    .timestamp(dateTime.minusMinutes(i))
                    .build()
            );
        }
        return resultList;
    }

    private void printLists(List<PowerProductionAggregation> allPowerAggregation, List<PowerProductionAggregation> resultList) {
        log.info("TIMESTAMP-EXPECTED: {}", allPowerAggregation.stream().map(PowerProductionAggregation::getTimestamp).toList());
        log.info("TIMESTAMP-RESULT: {}", resultList.stream().map(PowerProductionAggregation::getTimestamp).toList());

        log.info("AGGREGATED_POWER_STATIONS-EXPECTED: {}", allPowerAggregation.stream().map(PowerProductionAggregation::getAggregatedPowerStations).toList());
        log.info("AGGREGATED_POWER_STATIONS-RESULT: {}", resultList.stream().map(PowerProductionAggregation::getAggregatedPowerStations).toList());

        log.info("AGGREGATION_VALUE-EXPECTED: {}", allPowerAggregation.stream().map(PowerProductionAggregation::getAggregationValue).toList());
        log.info("AGGREGATION_VALUE-RESULT: {}", resultList.stream().map(PowerProductionAggregation::getAggregationValue).toList());
    }
}