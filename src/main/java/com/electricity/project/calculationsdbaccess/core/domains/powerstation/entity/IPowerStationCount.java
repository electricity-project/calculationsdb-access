package com.electricity.project.calculationsdbaccess.core.domains.powerstation.entity;

import com.electricity.project.calculationsdbaccess.api.powerstation.PowerStationState;


public interface IPowerStationCount {
    PowerStationState getState();

    Integer getNumber();
}
