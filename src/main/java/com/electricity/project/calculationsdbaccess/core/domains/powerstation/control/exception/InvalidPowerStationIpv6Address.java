package com.electricity.project.calculationsdbaccess.core.domains.powerstation.control.exception;

import java.text.MessageFormat;

public class InvalidPowerStationIpv6Address extends RuntimeException {
    public InvalidPowerStationIpv6Address(String action, String ipv6) {
        super(MessageFormat.format("Cannot find powers station for ipv6: {0}, and action: {1}", ipv6, action));
    }
}
