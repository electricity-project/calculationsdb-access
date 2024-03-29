package com.electricity.project.calculationsdbaccess.core.domains.powerstation.control;

import com.electricity.project.calculationsdbaccess.api.powerstation.PowerStationFilterDTO;
import com.electricity.project.calculationsdbaccess.api.powerstation.PowerStationState;
import com.electricity.project.calculationsdbaccess.core.domains.powerstation.control.exception.InvalidPowerStationId;
import com.electricity.project.calculationsdbaccess.core.domains.powerstation.control.exception.InvalidPowerStationIpv6Address;
import com.electricity.project.calculationsdbaccess.core.domains.powerstation.entity.IPowerStationCount;
import com.electricity.project.calculationsdbaccess.core.domains.powerstation.entity.PowerStation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class PowerStationService {

    private static final String ACTION_GET = "GET";
    private static final String ACTION_DISCONNECT = "DISCONNECT";
    private static final String ACTION_UPDATE_STATE = "UPDATE STATE";
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

    public PowerStation getPowerStationById(Long id) {
        return powerStationRepository.findById(id)
                .filter(PowerStation::isConnected)
                .orElseThrow(() -> new InvalidPowerStationId(ACTION_GET, id));
    }

    public PowerStation getPowerStationByIpv6(String ipv6Address) {
        return findPowerStationByIpv6(ipv6Address)
                .filter(PowerStation::isConnected)
                .orElseThrow(() -> new InvalidPowerStationIpv6Address(ACTION_GET, ipv6Address));
    }

    public Optional<PowerStation> findPowerStationByIpv6(String ipv6) {
        return powerStationRepository.findPowerStationByIpv6Address(ipv6);
    }

    public void deletePowerStationByIpv6(String ipv6) {
        powerStationRepository.deleteByIpv6Address(ipv6);
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

    public List<PowerStation> getPowerStations(PowerStationFilterDTO powerStationFilterDTO) {
        return powerStationRepository.findAll(powerStationRepository.filterSpecification(powerStationFilterDTO));
    }

    public PowerStation updatePowerStationState(String ipv6, PowerStationState state) {
        PowerStation powerStation = findPowerStationByIpv6(ipv6)
                .orElseThrow(() -> new InvalidPowerStationIpv6Address(ACTION_UPDATE_STATE, ipv6));
        powerStation.setState(state);
        return powerStationRepository.save(powerStation);
    }
}
