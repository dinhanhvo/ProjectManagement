package com.vodinh.prime.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegionDTO {
//    private Long id;
    private String regionId;
    private String name;
    private Boolean status;
}
