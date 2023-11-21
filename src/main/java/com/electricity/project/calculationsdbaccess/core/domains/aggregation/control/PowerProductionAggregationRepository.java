package com.electricity.project.calculationsdbaccess.core.domains.aggregation.control;

import com.electricity.project.calculationsdbaccess.core.domains.aggregation.entity.PowerProductionAggregation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PowerProductionAggregationRepository extends JpaRepository<PowerProductionAggregation, Long> {
}
