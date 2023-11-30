package com.electricity.project.calculationsdbaccess.core.domains.powerstation.control;

import com.electricity.project.calculationsdbaccess.core.domains.powerstation.control.exception.InvalidPowerStationIpv6Address;
import com.electricity.project.calculationsdbaccess.core.domains.powerstation.entity.PowerStation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PowerStationService {

    private static final String ACTION_DISCONNECT = "DISCONNECT";
    private final PowerStationRepository powerStationRepository;

    @Transactional
    public PowerStation savePowerStation(PowerStation powerStation) {
        return powerStationRepository.save(setPowerStationConnected(powerStation));
    }

    @Transactional
    public List<PowerStation> savePowerStations(List<PowerStation> powerStations) {
        return powerStationRepository.saveAll(powerStations.stream().map(this::setPowerStationConnected).toList());
    }

    private PowerStation setPowerStationConnected(PowerStation powerStation) {
        PowerStation powerStationToSave = findPowerStationByIpv6(powerStation.getIpv6Address())
                .map(savedPowerStation -> {
                    if (savedPowerStation.equals(powerStation)) {
                        return savedPowerStation;
                    }
                    deletePowerStationByIpv6(powerStation.getIpv6Address());
                    powerStationRepository.flush();

                    return null;
                }).orElse(powerStation);

        powerStationToSave.setState(powerStation.getState());
        powerStationToSave.setConnected(true);
        return powerStationToSave;
    }

    public long countPowerStations(){
        return powerStationRepository.countByIsConnected(true);
    }

    public Optional<PowerStation> findPowerStationByIpv6(String ipv6) {
        return powerStationRepository.findPowerStationByIpv6Address(ipv6);
    }

    public long deletePowerStationByIpv6(String ipv6) {
        return powerStationRepository.deleteByIpv6Address(ipv6);
    }

    @Transactional
    public void disconnectPowerStation(String ipv6Address) {
        powerStationRepository.save(getPowerStationToDisconnect(ipv6Address));
    }

    @Transactional
    public void disconnectPowerStations(List<String> ipv6Addresses) {
        powerStationRepository.saveAll(ipv6Addresses.stream().map(this::getPowerStationToDisconnect).toList());
    }

    private PowerStation getPowerStationToDisconnect(String ipv6Address) {
        PowerStation powerStation = findPowerStationByIpv6(ipv6Address)
                .orElseThrow(() -> new InvalidPowerStationIpv6Address(ACTION_DISCONNECT, ipv6Address));
        powerStation.setConnected(false);
        return powerStation;
    }

    public Optional<PowerStation> findPowerStationById(Long id) {
        return powerStationRepository.findById(id);
    }

    public Page<PowerStation> getPowerStations(Pageable pageable) {
        return powerStationRepository.findAll(pageable);
    }
}
