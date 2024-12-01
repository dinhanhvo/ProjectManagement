package com.vodinh.prime.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerRequest extends SignUpRequest {

    @NotBlank
    private String clientId;

    @NotBlank
    private String phone;

    private String position;

    @NotBlank
    private String companyName;

    private String companyPhone;

    private String address;

    private String city;

    private String profession;
}
