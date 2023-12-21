package com.electricity.project.calculationsdbaccess.core.domains.aggregation.control;

import com.electricity.project.calculationsdbaccess.api.aggregation.AggregationPeriodType;
import com.electricity.project.calculationsdbaccess.core.domains.aggregation.control.repository.PowerProductionAggregationRepository;
import com.electricity.project.calculationsdbaccess.core.domains.aggregation.entity.MinutesAggregation;
import com.electricity.project.calculationsdbaccess.core.domains.aggregation.entity.PowerProductionAggregation;
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

class PowerProductionAggregationServiceTest {

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
        powerProductionAggregationService = new PowerProductionAggregationService(aggregationMap);


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

        aggregationsWithMissingValues.remove(2);
        aggregationsWithMissingValues.remove(2);
        aggregationsWithMissingValues.remove(2);
        allPowerAggregation.set(5, builtEmptyAggregation(dateTime.minusMinutes(5)));
        allPowerAggregation.set(6, builtEmptyAggregation(dateTime.minusMinutes(6)));
        allPowerAggregation.set(7, builtEmptyAggregation(dateTime.minusMinutes(7)));

        PowerProductionAggregationRepository powerProductionRepositories = Mockito.mock(PowerProductionAggregationRepository.class);
        Mockito.when(powerProductionRepositories.findByOrderByTimestampAsc(any(LocalDateTime.class))).thenReturn(aggregationsWithMissingValues);

        aggregationMap.put(AggregationPeriodType.MINUTE, powerProductionRepositories);
        powerProductionAggregationService = new PowerProductionAggregationService(aggregationMap);

        List<PowerProductionAggregation> resultList = powerProductionAggregationService.getLastRecords(AggregationPeriodType.MINUTE, duration);

        assertEquals(allPowerAggregation.size(), resultList.size());
        for (int i = 0; i < resultList.size(); i++) {
            assertEquals(allPowerAggregation.get(i).getAggregatedPowerStations(), resultList.get(i).getAggregatedPowerStations());
            assertEquals(allPowerAggregation.get(i).getAggregationValue(), resultList.get(i).getAggregationValue());
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

        PowerProductionAggregationRepository powerProductionRepositories = Mockito.mock(PowerProductionAggregationRepository.class);
        Mockito.when(powerProductionRepositories.findByOrderByTimestampAsc(any(LocalDateTime.class))).thenReturn(aggregationsWithMissingValues);

        aggregationMap.put(AggregationPeriodType.MINUTE, powerProductionRepositories);
        powerProductionAggregationService = new PowerProductionAggregationService(aggregationMap);

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
}