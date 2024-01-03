package com.electricity.project.calculationsdbaccess.core.domains.powerstation.control;

import com.electricity.project.calculationsdbaccess.core.domains.powerstation.entity.IPowerStationCount;
import com.electricity.project.calculationsdbaccess.core.domains.powerstation.entity.PowerStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PowerStationRepository extends JpaRepository<PowerStation, Long> {

    Optional<PowerStation> findPowerStationByIpv6Address(String ipv6);

    long deleteByIpv6Address(String ipv6Address);

    @Query(value = "SELECT state, COUNT(state) as number FROM power_station WHERE is_connected = true GROUP BY state", nativeQuery = true)
    List<IPowerStationCount> countPowerStationsByState();

}
