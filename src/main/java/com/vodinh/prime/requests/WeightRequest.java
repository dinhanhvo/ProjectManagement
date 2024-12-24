package com.vodinh.prime.requests;

import com.vodinh.prime.enums.WeightStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeightRequest {

    private Long id;
    private String name;

    @NotBlank
    private String serialNumber;

    @NotBlank
    private String model;

    @NotBlank
    private Double quyCach;

    private WeightStatus status = WeightStatus.INACTIVE;

    private LocalDateTime sellAt;

    @NotBlank
    private Long contactId;

    private Long lineId; // ID của Line
    private String weightId;
}
