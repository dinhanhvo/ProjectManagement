package com.vodinh.prime.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum WeightStatus {
    ERROR,
    INACTIVE,
    PAUSE,
    RUNNING,
    ACTIVE;

    @JsonCreator
    public static WeightStatus fromValue(String value) {
        for (WeightStatus status : WeightStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid value for WeightStatus: " + value);
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
