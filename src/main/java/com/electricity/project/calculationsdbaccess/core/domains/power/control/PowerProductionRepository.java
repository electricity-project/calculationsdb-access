package com.electricity.project.calculationsdbaccess.core.domains.power.control;

import com.electricity.project.calculationsdbaccess.core.domains.power.entity.PowerProduction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PowerProductionRepository extends JpaRepository<PowerProduction, Long> {
    List<PowerProduction> getByIpv6OrderByTimestampDesc(String ipv6, Pageable pageable);
}