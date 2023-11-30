package com.electricity.project.calculationsdbaccess.core.domains.powerstation.control;

import com.electricity.project.calculationsdbaccess.core.domains.powerstation.entity.PowerStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PowerStationRepository extends JpaRepository<PowerStation, Long> {

    Optional<PowerStation> findPowerStationByIpv6Address(String ipv6);

    long deleteByIpv6Address(String ipv6Address);

    long countByIsConnected(boolean isConnected);

}
