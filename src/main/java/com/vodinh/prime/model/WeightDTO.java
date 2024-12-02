package com.vodinh.prime.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeightDTO extends BaseDTO{
    private Long id;
    @NotBlank
    private String serialNumber;
    @NotBlank
    private String model;
    @NotBlank
    private Double quyCach;
    private LocalDateTime sellAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long contactId;
    private String contactName;
    private String contactUsername;
}
