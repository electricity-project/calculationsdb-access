package com.electricity.project.calculationsdbaccess.core.domains.powerstation.boundary;

import com.electricity.project.calculationsdbaccess.api.error.ErrorDTO;
import com.electricity.project.calculationsdbaccess.api.powerstation.PowerStationDTO;
import com.electricity.project.calculationsdbaccess.core.domains.powerstation.control.PowerStationMapper;
import com.electricity.project.calculationsdbaccess.core.domains.powerstation.control.PowerStationService;
import com.electricity.project.calculationsdbaccess.core.domains.powerstation.control.exception.IncorrectPowerStationType;
import com.electricity.project.calculationsdbaccess.core.domains.powerstation.control.exception.InvalidPowerStationIpv6Address;
import com.electricity.project.calculationsdbaccess.core.domains.powerstation.entity.PowerStation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/power-station")
@RequiredArgsConstructor
@Slf4j
public class PowerStationResource {

    private final PowerStationService powerStationService;

    @PostMapping
    public ResponseEntity<PowerStationDTO> connectPowerStation(@RequestBody PowerStationDTO powerStationDTO) {
        PowerStation savedPowerStation = powerStationService.savePowerStation(PowerStationMapper.mapToEntity(powerStationDTO));
        return ResponseEntity.ok(PowerStationMapper.mapToDTO(savedPowerStation));
    }

    @PostMapping("/list")
    public ResponseEntity<List<PowerStationDTO>> connectPowerStations(@RequestBody List<PowerStationDTO> powerStationDTOs) {
        List<PowerStation> savedPowerStations = powerStationService.savePowerStations(
                powerStationDTOs.stream().map(PowerStationMapper::mapToEntity).toList()
        );
        return ResponseEntity.ok(savedPowerStations.stream().map(PowerStationMapper::mapToDTO).toList());
    }

    @GetMapping
    public ResponseEntity<List<PowerStationDTO>> getPowerStations(Pageable pageable) {
        return ResponseEntity.ok(
                powerStationService.getPowerStations(pageable)
                        .stream()
                        .map(PowerStationMapper::mapToDTO)
                        .toList()
        );
    }

    @GetMapping("/disconnect")
    public ResponseEntity<Void> disconnectPowerStation(@RequestParam String ipv6Address) {
        powerStationService.disconnectPowerStation(ipv6Address);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/disconnect/list")
    public ResponseEntity<Void> disconnectPowerStations(@RequestBody List<String> ipv6Addresses) {
        powerStationService.disconnectPowerStations(ipv6Addresses);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(IncorrectPowerStationType.class)
    private ResponseEntity<ErrorDTO> handleIncorrectPowerStationType(IncorrectPowerStationType exception) {
        log.error("Incorrect power station type", exception);
        return ResponseEntity.internalServerError().body(ErrorDTO.builder().error(exception.getMessage()).build());
    }

    @ExceptionHandler(InvalidPowerStationIpv6Address.class)
    private ResponseEntity<ErrorDTO> handleInvalidPowerStationIpv6Address(InvalidPowerStationIpv6Address exception) {
        log.error("Invalid power station ipv6", exception);
        return ResponseEntity.badRequest().body(ErrorDTO.builder().error(exception.getMessage()).build());
    }
}
