package com.electricity.project.calculationsdbaccess.core.domains.powerstation.control;

import com.electricity.project.calculationsdbaccess.core.domains.powerstation.entity.PowerStation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PowerStationService {

    private final PowerStationRepository powerStationRepository;

    public PowerStation savePowerStation(PowerStation powerStation) {
        return powerStationRepository.save(powerStation);
    }

    public List<PowerStation> savePowerStations(List<PowerStation> powerStation) {
        return powerStationRepository.saveAll(powerStation);
    }

    public Optional<PowerStation> findPowerStationById(Long id) {
        return powerStationRepository.findById(id);
    }

    public Page<PowerStation> getPowerStations(Pageable pageable){
        return powerStationRepository.findAll(pageable);
    }
}
