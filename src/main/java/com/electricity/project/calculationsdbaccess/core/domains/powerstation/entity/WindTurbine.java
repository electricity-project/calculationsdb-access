package com.electricity.project.calculationsdbaccess.core.domains.powerstation.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class WindTurbine extends PowerStation {

    @Column(nullable = false)
    private long bladeLength;

}
