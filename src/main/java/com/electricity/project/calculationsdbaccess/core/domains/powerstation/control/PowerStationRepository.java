package com.electricity.project.calculationsdbaccess.core.domains.powerstation.control;

import com.electricity.project.calculationsdbaccess.api.powerstation.PowerStationFilterDTO;
import com.electricity.project.calculationsdbaccess.api.powerstation.PowerStationState;
import com.electricity.project.calculationsdbaccess.core.domains.powerstation.entity.IPowerStationCount;
import com.electricity.project.calculationsdbaccess.core.domains.powerstation.entity.PowerStation;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PowerStationRepository extends JpaRepository<PowerStation, Long>, JpaSpecificationExecutor<PowerStation> {

    default Specification<PowerStation> filterSpecification(PowerStationFilterDTO powerStationFilterDTO) {
        return (root, query, criteriaBuilder) -> {
            Path<String> ipv6Address = root.get("ipv6Address");
            Predicate[] ipv6Predicates = powerStationFilterDTO.getIpv6Patterns().stream()
                    .map(ipv6Pattern -> criteriaBuilder.equal(ipv6Address, ipv6Pattern))
                    .toArray(Predicate[]::new);

            Path<PowerStationState> state = root.get("state");
            Predicate[] statePredicates = powerStationFilterDTO.getStatePatterns().stream()
                    .map(statePattern -> criteriaBuilder.equal(state, statePattern))
                    .toArray(Predicate[]::new);

            Predicate[] typePredicates = powerStationFilterDTO.getTypePatterns().stream()
                    .map(statePattern -> criteriaBuilder.equal(root.type(), criteriaBuilder.literal(statePattern.powerStationClass)))
                    .toArray(Predicate[]::new);

            return criteriaBuilder.and(
                    ipv6Predicates.length == 0 ? criteriaBuilder.and() : criteriaBuilder.or(ipv6Predicates),
                    statePredicates.length == 0 ? criteriaBuilder.and() : criteriaBuilder.or(statePredicates),
                    typePredicates.length == 0 ? criteriaBuilder.and() : criteriaBuilder.or(typePredicates)
            );
        };

    }

    Optional<PowerStation> findPowerStationByIpv6Address(String ipv6);

    long deleteByIpv6Address(String ipv6Address);

    @Query(value = "SELECT state, COUNT(state) as number FROM power_station WHERE is_connected = true GROUP BY state", nativeQuery = true)
    List<IPowerStationCount> countPowerStationsByState();

}
