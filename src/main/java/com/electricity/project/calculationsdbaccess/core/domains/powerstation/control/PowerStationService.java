package com.electricity.project.calculationsdbaccess.core.domains.powerstation.control;

import com.electricity.project.calculationsdbaccess.api.powerstation.PowerStationFilterDTO;
import com.electricity.project.calculationsdbaccess.api.powerstation.PowerStationState;
import com.electricity.project.calculationsdbaccess.core.domains.powerstation.control.exception.InvalidPowerStationIpv6Address;
import com.electricity.project.calculationsdbaccess.core.domains.powerstation.entity.IPowerStationCount;
import com.electricity.project.calculationsdbaccess.core.domains.powerstation.entity.PowerStation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Map<PowerStationState, Integer> countPowerStationsByStates() {
        Map<PowerStationState, Integer> powerStationsStates = powerStationRepository.countPowerStationsByState()
                .stream()
                .collect(Collectors.toMap(IPowerStationCount::getState, IPowerStationCount::getNumber));

        Arrays.stream(PowerStationState.values())
                .forEach(state -> powerStationsStates.putIfAbsent(state, 0));

        return powerStationsStates;
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

    public Page<PowerStation> getPowerStations(PowerStationFilterDTO powerStationFilterDTO, Pageable pageable) {
        return powerStationRepository.findAll(powerStationRepository.filterSpecification(powerStationFilterDTO), pageable);
    }
}
