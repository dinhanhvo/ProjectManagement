package com.vodinh.prime.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LineDTO extends BaseDTO {
    private Long id;
    private String lineId;
    private String name;
    private Long clientId;
    private String clientUsername;
    private String clientName;
    private Boolean status;
}
