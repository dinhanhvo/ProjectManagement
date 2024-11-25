package com.vodinh.prime.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private Long id;
    private String title;
    private String project;
    private String description;
    private String assignedTo;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
