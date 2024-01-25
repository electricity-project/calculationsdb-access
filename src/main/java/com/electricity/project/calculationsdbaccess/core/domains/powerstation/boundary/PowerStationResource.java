package com.electricity.project.calculationsdbaccess.core.domains.powerstation.boundary;

import com.electricity.project.calculationsdbaccess.api.error.ErrorDTO;
import com.electricity.project.calculationsdbaccess.api.powerstation.PowerStationDTO;
import com.electricity.project.calculationsdbaccess.api.powerstation.PowerStationFilterDTO;
import com.electricity.project.calculationsdbaccess.api.powerstation.PowerStationState;
import com.electricity.project.calculationsdbaccess.core.domains.powerstation.control.PowerStationMapper;
import com.electricity.project.calculationsdbaccess.core.domains.powerstation.control.PowerStationService;
import com.electricity.project.calculationsdbaccess.core.domains.powerstation.control.exception.IncorrectPowerStationType;
import com.electricity.project.calculationsdbaccess.core.domains.powerstation.control.exception.InvalidPowerStationId;
import com.electricity.project.calculationsdbaccess.core.domains.powerstation.control.exception.InvalidPowerStationIpv6Address;
import com.electricity.project.calculationsdbaccess.core.domains.powerstation.entity.PowerStation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/{id}")
    public ResponseEntity<PowerStationDTO> getPowerStationById(@PathVariable Long id) {
        return ResponseEntity.ok(PowerStationMapper.mapToDTO(powerStationService.getPowerStationById(id)));
    }

    @PatchMapping
    public ResponseEntity<PowerStationDTO> updatePowerStationStateByIpv6(@RequestParam String ipv6, @RequestParam PowerStationState state){
        return ResponseEntity.ok(PowerStationMapper.mapToDTO(powerStationService.updatePowerStationState(ipv6, state)));
    }

    @GetMapping
    public ResponseEntity<PowerStationDTO> getPowerStationByIpv6(String ipv6) {
        return ResponseEntity.ok(PowerStationMapper.mapToDTO(powerStationService.getPowerStationByIpv6(ipv6)));
    }

    @PostMapping("/all")
    public ResponseEntity<Page<PowerStationDTO>> getPowerStations(
            @RequestParam int size,
            @RequestParam int page,
            Sort sort,
            @RequestBody PowerStationFilterDTO powerStationFilterDTO
    ) {
        return ResponseEntity.ok(powerStationService.getPowerStations(powerStationFilterDTO, PageRequest.of(page, size, sort))
                .map(PowerStationMapper::mapToDTO));
    }

    @PostMapping("/all_filter_list")
    public ResponseEntity<List<PowerStationDTO>> getPowerStations(@RequestBody PowerStationFilterDTO powerStationFilterDTO) {
        return ResponseEntity.ok(powerStationService.getPowerStations(powerStationFilterDTO).stream()
                .map(PowerStationMapper::mapToDTO).toList());
    }

    @GetMapping("/count")
    public ResponseEntity<Map<PowerStationState, Integer>> getConnectedPowerStationsCount() {
        return ResponseEntity.ok(powerStationService.countPowerStationsByStates());
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

    @ExceptionHandler(InvalidPowerStationId.class)
    private ResponseEntity<ErrorDTO> handleInvalidPowerStationId(InvalidPowerStationId exception) {
        log.error("Invalid power station id", exception);
        return ResponseEntity.badRequest().body(ErrorDTO.builder().error(exception.getMessage()).build());
    }
}
