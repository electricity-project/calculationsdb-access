package com.electricity.project.calculationsdbaccess.core.domains.powerstation.control.exception;

import java.text.MessageFormat;

public class IncorrectPowerStationType extends RuntimeException {
    public IncorrectPowerStationType(Object value) {
        super(MessageFormat.format("Incorrect type for object: {0}", value));
    }
}
