package com.electricity.project.calculationsdbaccess.infrastructure;

import com.electricity.project.calculationsdbaccess.api.aggregation.AggregationPeriodType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilterDateParser {
    public static LocalDateTime createFilterDate(AggregationPeriodType periodType, Integer duration) {
        return switch (periodType) {
            case MINUTE -> LocalDateTime.now().minusMinutes(duration);
            case HOUR -> LocalDateTime.now().minusHours(duration);
            case DAY -> LocalDateTime.now().minusDays(duration);
        };
    }
}