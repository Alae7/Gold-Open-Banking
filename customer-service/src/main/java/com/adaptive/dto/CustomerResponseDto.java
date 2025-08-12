package com.adaptive.dto;


import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CustomerResponseDto {

    private String uuid;

    private String  firstName;

    private String  lastName;

    private String cin;

    private LocalDate birthDate;

    private String  email;

    private String  password;

    private String  phone;

    private String image;

    private String  address;

    private String  city;


}
