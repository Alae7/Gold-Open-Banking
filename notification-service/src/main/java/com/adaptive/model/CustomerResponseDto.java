package com.adaptive.model;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CustomerResponseDto {

    private String uuid;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private AddressResponseDto address;


}
