package com.vodinh.prime.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {
    @NotBlank
    @Size(min = 4, max = 55)
    private String title;

    @NotBlank
    private Long project;

    @NotBlank
    @Size(min = 10, max = 1023)
    private String description;

    private Long assignedTo;

    private String status;

}
