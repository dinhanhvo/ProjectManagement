package com.vodinh.prime.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineRequest {
    @NotBlank(message = "Line ID cannot be empty")
    @Size(max = 100, message = "Line ID cannot exceed 100 characters")
    private String lineId;

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;

    @NotBlank(message = "Status cannot be empty")
    private Boolean status;

    @NotBlank(message = "Client ID cannot be empty")
    private Long clientId;
}
