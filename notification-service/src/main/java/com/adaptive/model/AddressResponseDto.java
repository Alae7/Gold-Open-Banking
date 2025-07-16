package com.adaptive.model;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class AddressResponseDto {

    private String street;
    private String city;
    private String houseNumber;
    private String postalCode;
    private String uuid;

}
