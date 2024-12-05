package com.vodinh.prime.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegionDTO {
    private Long id;
    @NotBlank
    private String regionId;
    @NotBlank
    private String name;
    private Boolean status;
}
