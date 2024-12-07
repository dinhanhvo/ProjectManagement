package com.vodinh.prime.requests;

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

    @NotBlank
    private String serialNumber;

    @NotBlank
    private String model;

    @NotBlank
    private Double quyCach;

    private LocalDateTime sellAt;

    private Long contactId;

    private Long lineId; // ID của Line
}
