package com.vodinh.prime.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

public enum TaskStatus {
    COMPLETED,
    PROCESSING,
    OPEN;

    @JsonCreator
    public static TaskStatus fromValue(String value) {
        if (StringUtils.isBlank(value)) {
            return TaskStatus.OPEN;
        }
        return TaskStatus.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
