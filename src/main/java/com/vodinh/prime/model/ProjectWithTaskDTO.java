package com.vodinh.prime.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectWithTaskDTO extends ProjectDTO {

    List<TaskDTO> taskDTOS = new ArrayList<>();

    // Constructor with all fields (extends ProjectDTO)
    public ProjectWithTaskDTO(Long id, String name, String owner, String description, LocalDateTime createdAt, LocalDateTime updatedAt, List<TaskDTO> taskDTOS) {
        super(id, name, owner, description, createdAt, updatedAt); // Call the parent constructor
        this.taskDTOS = taskDTOS;
    }

    public ProjectWithTaskDTO(Long id, String name, String owner, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, name, owner, description, createdAt, updatedAt); // Call the parent constructor
    }
}
