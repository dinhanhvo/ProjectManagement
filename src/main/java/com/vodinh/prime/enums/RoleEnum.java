package com.vodinh.prime.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

public enum RoleEnum {
    ROLE_USER, ROLE_CUSTOMER, ROLE_ADMIN;
    @JsonCreator
    public static RoleEnum fromValue(String value) {
        if (StringUtils.isBlank(value)) {
            return RoleEnum.ROLE_USER;
        }
        return RoleEnum.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
