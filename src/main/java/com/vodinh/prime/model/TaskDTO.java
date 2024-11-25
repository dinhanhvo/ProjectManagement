package com.vodinh.prime.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDTO {
    private Long id;
    private String title;
    private String project;
    private String description;
    private String assignedTo;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

//    public TaskDTO(Long id, String title, String description, String assignedTo, String status,
//                   LocalDateTime createdAt, LocalDateTime updatedAt) {
//        this.id = id;
//        this.title = title;
//        this.description = description;
//        this.assignedTo = assignedTo;
//        this.status = status;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
//    }
}
