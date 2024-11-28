package com.vodinh.prime.model;

import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDTO extends BaseDTO {
    private Long id;
    private String title;
    private String project;
    private String description;
    private String assignedTo;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
