package com.electricity.project.calculationsdbaccess.core.domains.powerstation.control.exception;

import java.text.MessageFormat;

public class InvalidPowerStationId extends RuntimeException {
    public InvalidPowerStationId(String action, Long id) {
        super(MessageFormat.format("Cannot find powers station for id: {0}, and action: {1}", id, action));
    }
}
