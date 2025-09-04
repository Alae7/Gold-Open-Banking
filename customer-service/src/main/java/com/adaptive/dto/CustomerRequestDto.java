package com.adaptive.dto;

import com.adaptive.entity.TypeCompte;
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

    private String  address;

    private String  city;

    private String  banqueUuid;

    private TypeCompte typeCompte;

}
