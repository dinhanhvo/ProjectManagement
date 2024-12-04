package com.vodinh.prime.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    @NotBlank
    @Size(min = 2, max = 55)
    private String name;

    @NotBlank
    @Size(min = 3, max = 55)
    private String username;

    @NotBlank
    @Size(max = 55)
    @Email
    private String email;

    @NotBlank
    @Size(min = 3, max = 55)
    private String password;

}
