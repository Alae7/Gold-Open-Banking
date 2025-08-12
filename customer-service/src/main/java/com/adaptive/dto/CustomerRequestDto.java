package com.adaptive.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CustomerRequestDto {

    private String  firstName;

    private String  lastName;

    private String cin;

    private LocalDate birthDate;

    private String  email;

    private String  password;

    private String  phone;

    private MultipartFile image;

    private String  address;

    private String  city;

}
