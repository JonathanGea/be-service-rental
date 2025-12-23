package com.gea.app.vehicle.enum_;

/**
 * Supported vehicle status values.
 */
public enum VehicleStatus {
    AVAILABLE("available"),
    RENTED("rented"),
    UNAVAILABLE("unavailable"),
    MAINTENANCE("maintenance");

    private final String value;

    VehicleStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static VehicleStatus fromValue(String raw) {
        if (raw == null) {
            return null;
        }
        for (VehicleStatus status : values()) {
            if (status.value.equalsIgnoreCase(raw)) {
                return status;
            }
        }
        return null;
    }
}
