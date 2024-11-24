package com.vodinh.prime.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
    private Long id;
    private String name;
    private String owner;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
