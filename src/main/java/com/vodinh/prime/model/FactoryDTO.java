package com.vodinh.prime.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FactoryDTO extends BaseDTO {
    private Long id;
    private String factoryId;
    private String name;
    private String regionId;
    private String regionName;
    private Boolean status;
}
