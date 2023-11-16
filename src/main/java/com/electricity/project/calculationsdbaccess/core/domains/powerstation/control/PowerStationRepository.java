package com.electricity.project.calculationsdbaccess.core.domains.powerstation.control;

import com.electricity.project.calculationsdbaccess.core.domains.powerstation.entity.PowerStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PowerStationRepository extends JpaRepository<PowerStation, Long> {

}
