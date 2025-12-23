package com.gea.app.rental.enum_;

/**
 * Supported rental status values.
 */
public enum RentalStatus {
    PENDING("pending"),
    ACTIVE("active"),
    COMPLETED("completed"),
    CANCELLED("cancelled");

    private final String value;

    RentalStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static RentalStatus fromValue(String raw) {
        if (raw == null) {
            return null;
        }
        for (RentalStatus status : values()) {
            if (status.value.equalsIgnoreCase(raw)) {
                return status;
            }
        }
        return null;
    }
}
