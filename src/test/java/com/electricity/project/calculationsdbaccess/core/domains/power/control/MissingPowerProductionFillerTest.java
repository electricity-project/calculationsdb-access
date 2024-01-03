package com.electricity.project.calculationsdbaccess.core.domains.power.control;

import com.electricity.project.calculationsdbaccess.api.powerstation.PowerStationState;
import com.electricity.project.calculationsdbaccess.core.domains.power.entity.PowerProduction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MissingPowerProductionFillerTest {

    private final MissingPowerProductionFiller missingPowerProductionFiller = new MissingPowerProductionFiller();
    private int duration;
    private LocalDateTime dateTime;
    private List<PowerProduction> powerProductions;
    private static final String IPV6 = "::1";

    @BeforeEach
    void setUp() {
        dateTime = LocalDateTime.now();
        duration = 10;
        powerProductions = createListWithPowerProduction(dateTime, duration);
    }

    @Test
    void fillMissingTimestamps_WhenMissingDataInMiddle_ShouldReturnFilledList() {
        List<PowerProduction> powerProductionWithMissingValues = new java.util.ArrayList<>(List.copyOf(powerProductions));
        powerProductionWithMissingValues.remove(1);
        powerProductionWithMissingValues.remove(1);
        powerProductionWithMissingValues.remove(1);
        powerProductions.set(1, buildEmptyPowerProduction(dateTime.minusMinutes(1)));
        powerProductions.set(2, buildEmptyPowerProduction(dateTime.minusMinutes(2)));
        powerProductions.set(3, buildEmptyPowerProduction(dateTime.minusMinutes(3)));

        List<PowerProduction> resultList = missingPowerProductionFiller.fillMissingTimestamps(IPV6, duration, powerProductionWithMissingValues);

        assertEquals(powerProductions.size(), resultList.size());
        for (int i = 0; i < resultList.size(); i++) {
            assertEquals(powerProductions.get(i).getState(), resultList.get(i).getState());
            assertEquals(powerProductions.get(i).getProducedPower(), resultList.get(i).getProducedPower());
            assertEquals(powerProductions.get(i).getTimestamp(), resultList.get(i).getTimestamp());
        }
    }

    @Test
    void fillMissingTimestamps_WhenMissingDataBefore_ShouldReturnFilledList() {
        List<PowerProduction> powerProductionWithMissingValues = new java.util.ArrayList<>(List.copyOf(powerProductions));
        powerProductionWithMissingValues.removeFirst();
        powerProductionWithMissingValues.removeFirst();
        powerProductionWithMissingValues.removeFirst();
        powerProductions.set(0, buildEmptyPowerProduction(dateTime.minusMinutes(0)));
        powerProductions.set(1, buildEmptyPowerProduction(dateTime.minusMinutes(1)));
        powerProductions.set(2, buildEmptyPowerProduction(dateTime.minusMinutes(2)));

        List<PowerProduction> resultList = missingPowerProductionFiller.fillMissingTimestamps(IPV6, duration, powerProductionWithMissingValues);

        assertEquals(powerProductions.size(), resultList.size());
        for (int i = 0; i < resultList.size(); i++) {
            assertEquals(powerProductions.get(i).getState(), resultList.get(i).getState());
            assertEquals(powerProductions.get(i).getProducedPower(), resultList.get(i).getProducedPower());
        }
    }

    @Test
    void fillMissingTimestamps_WhenMissingDataAfter_ShouldReturnFilledList() {
        List<PowerProduction> powerProductionWithMissingValues = new java.util.ArrayList<>(List.copyOf(powerProductions));
        powerProductionWithMissingValues.removeLast();
        powerProductionWithMissingValues.removeLast();
        powerProductionWithMissingValues.removeLast();
        powerProductions.set(powerProductions.size() - 1, buildEmptyPowerProduction(dateTime.minusMinutes(powerProductions.size() - 1)));
        powerProductions.set(powerProductions.size() - 2, buildEmptyPowerProduction(dateTime.minusMinutes(powerProductions.size() - 2)));
        powerProductions.set(powerProductions.size() - 3, buildEmptyPowerProduction(dateTime.minusMinutes(powerProductions.size() - 3)));

        List<PowerProduction> resultList = missingPowerProductionFiller.fillMissingTimestamps(IPV6, duration, powerProductionWithMissingValues);

        assertEquals(powerProductions.size(), resultList.size());
        for (int i = 0; i < resultList.size(); i++) {
            assertEquals(powerProductions.get(i).getState(), resultList.get(i).getState());
            assertEquals(powerProductions.get(i).getProducedPower(), resultList.get(i).getProducedPower());
            assertEquals(powerProductions.get(i).getTimestamp(), resultList.get(i).getTimestamp());
        }
    }

    @Test
    void fillMissingTimestamps_WhenMissingDataBeforeAndInMiddle_ShouldReturnFilledList() {
        List<PowerProduction> powerProductionWithMissingValues = new java.util.ArrayList<>(List.copyOf(powerProductions));
        powerProductionWithMissingValues.removeFirst();
        powerProductionWithMissingValues.removeFirst();
        powerProductionWithMissingValues.removeFirst();
        powerProductions.set(0, buildEmptyPowerProduction(dateTime.minusMinutes(0)));
        powerProductions.set(1, buildEmptyPowerProduction(dateTime.minusMinutes(1)));
        powerProductions.set(2, buildEmptyPowerProduction(dateTime.minusMinutes(2)));

        powerProductionWithMissingValues.remove(2);
        powerProductionWithMissingValues.remove(2);
        powerProductionWithMissingValues.remove(2);
        powerProductions.set(5, buildEmptyPowerProduction(dateTime.minusMinutes(5)));
        powerProductions.set(6, buildEmptyPowerProduction(dateTime.minusMinutes(6)));
        powerProductions.set(7, buildEmptyPowerProduction(dateTime.minusMinutes(7)));

        List<PowerProduction> resultList = missingPowerProductionFiller.fillMissingTimestamps(IPV6, duration, powerProductionWithMissingValues);

        assertEquals(powerProductions.size(), resultList.size());
        for (int i = 0; i < resultList.size(); i++) {
            assertEquals(powerProductions.get(i).getState(), resultList.get(i).getState());
            assertEquals(powerProductions.get(i).getProducedPower(), resultList.get(i).getProducedPower());
        }
    }

    @Test
    void fillMissingTimestamps_WhenMissingDataBeforeInMiddleAndAfter_ShouldReturnFilledList() {
        List<PowerProduction> powerProductionWithMissingValues = new java.util.ArrayList<>(List.copyOf(powerProductions));
        powerProductionWithMissingValues.removeFirst();
        powerProductionWithMissingValues.removeFirst();
        powerProductions.set(0, buildEmptyPowerProduction(dateTime.minusMinutes(0)));
        powerProductions.set(1, buildEmptyPowerProduction(dateTime.minusMinutes(1)));

        powerProductionWithMissingValues.remove(2);
        powerProductionWithMissingValues.remove(2);
        powerProductions.set(4, buildEmptyPowerProduction(dateTime.minusMinutes(4)));
        powerProductions.set(5, buildEmptyPowerProduction(dateTime.minusMinutes(5)));

        powerProductionWithMissingValues.removeLast();
        powerProductionWithMissingValues.removeLast();
        powerProductions.set(powerProductions.size() - 1, buildEmptyPowerProduction(dateTime.minusMinutes(powerProductions.size() - 1)));
        powerProductions.set(powerProductions.size() - 2, buildEmptyPowerProduction(dateTime.minusMinutes(powerProductions.size() - 2)));

        List<PowerProduction> resultList = missingPowerProductionFiller.fillMissingTimestamps(IPV6, duration, powerProductionWithMissingValues);

        assertEquals(powerProductions.size(), resultList.size());
        for (int i = 0; i < resultList.size(); i++) {
            assertEquals(powerProductions.get(i).getState(), resultList.get(i).getState());
            assertEquals(powerProductions.get(i).getProducedPower(), resultList.get(i).getProducedPower());
        }
    }

    private static List<PowerProduction> createListWithPowerProduction(LocalDateTime dateTime, int duration) {
        List<PowerProduction> resultList = new LinkedList<>();

        for (int i = 0; i < duration; i++) {
            resultList.add(PowerProduction.builder()
                    .ipv6(IPV6)
                    .state(PowerStationState.WORKING)
                    .producedPower(100L + i * 10L)
                    .timestamp(dateTime.minusMinutes(i))
                    .build()
            );
        }
        return resultList;
    }

    private static PowerProduction buildEmptyPowerProduction(LocalDateTime dateTime) {
        return PowerProduction.builder()
                .ipv6(IPV6)
                .state(null)
                .producedPower(null)
                .timestamp(dateTime)
                .build();
    }
}