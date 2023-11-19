package com.electricity.project.calculationsdbaccess.core.domains.powerstation.entity;

import com.electricity.project.calculationsdbaccess.api.powerstation.PowerStationState;
import com.electricity.project.calculationsdbaccess.core.domains.power.entity.PowerProduction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PowerStation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "ipv6Address", length = 39, unique = true, nullable = false)
    private String ipv6Address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PowerStationState state;

    @Column(nullable = false)
    private LocalDateTime creationTime;

    @Column(nullable = false)
    private double maxPower;

    @Column(nullable = false)
    private boolean isConnected;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "power_station_ipv6", referencedColumnName = "ipv6Address", nullable = false, insertable = false, updatable = false)
    private Set<PowerProduction> powerProductions = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PowerStation that)) return false;

        if (Double.compare(maxPower, that.maxPower) != 0) return false;
        if (!ipv6Address.equals(that.ipv6Address)) return false;
        return creationTime.equals(that.creationTime);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = ipv6Address.hashCode();
        result = 31 * result + creationTime.hashCode();
        temp = Double.doubleToLongBits(maxPower);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
