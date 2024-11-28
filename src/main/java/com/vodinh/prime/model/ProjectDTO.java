package com.vodinh.prime.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO extends BaseDTO {
    private Long id;
    private String name;
    private String owner;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
