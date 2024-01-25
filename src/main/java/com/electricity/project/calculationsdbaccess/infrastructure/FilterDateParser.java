package com.electricity.project.calculationsdbaccess.infrastructure;

import com.electricity.project.calculationsdbaccess.api.aggregation.AggregationPeriodType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilterDateParser {
    public static ZonedDateTime createFilterDate(AggregationPeriodType periodType, Integer duration) {
        return switch (periodType) {
            case MINUTE -> ZonedDateTime.now().minusMinutes(duration);
            case HOUR -> ZonedDateTime.now().minusHours(duration);
            case DAY -> ZonedDateTime.now().minusDays(duration);
        };
    }
}