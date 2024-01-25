package com.electricity.project.calculationsdbaccess.api.powerstation;

import com.electricity.project.calculationsdbaccess.core.domains.powerstation.entity.PowerStation;
import com.electricity.project.calculationsdbaccess.core.domains.powerstation.entity.SolarPanel;
import com.electricity.project.calculationsdbaccess.core.domains.powerstation.entity.WindTurbine;

public enum PowerStationType {
    WIND_TURBINE(WindTurbine.class), SOLAR_PANEL(SolarPanel.class);

    public final Class<? extends PowerStation> powerStationClass;

    PowerStationType(Class<? extends PowerStation> powerStationClass) {
        this.powerStationClass = powerStationClass;
    }
}
