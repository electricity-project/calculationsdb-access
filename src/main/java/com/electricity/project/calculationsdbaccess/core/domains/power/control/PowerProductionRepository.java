package com.electricity.project.calculationsdbaccess.core.domains.power.control;

import com.electricity.project.calculationsdbaccess.core.domains.power.entity.PowerProduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PowerProductionRepository extends JpaRepository<PowerProduction, Long> {

    @Query("select p from PowerProduction p where p.ipv6 = :ipv6 and p.timestamp >=:filterDate order by p.timestamp DESC")
    List<PowerProduction> getByIpv6OrderByTimestampDesc(String ipv6, LocalDateTime filterDate);

    List<PowerProduction> getByTimestampGreaterThanEqualAndTimestampLessThan(LocalDateTime startingDate, LocalDateTime endingDate);
}