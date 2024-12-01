package com.vodinh.prime.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerRequest extends SignUpRequest {
//    private String clientId;

    private String phone;

    private String position;

    private String companyName;

    private String companyPhone;

    private String address;

    private String city;

    private String profession;
}
