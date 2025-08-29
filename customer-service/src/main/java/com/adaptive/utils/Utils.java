package com.adaptive.utils;

import com.adaptive.config.ExecuteRequest;
import com.adaptive.dto.ClientRequestDto;
import com.adaptive.dto.CompteRequestDto;
import com.adaptive.dto.CustomerResponseDto;
import com.adaptive.entity.NameApi;
import com.adaptive.entity.TypeCompte;

public class Utils {


    public static ExecuteRequest createExecuteRequest(CustomerResponseDto customerResponseDto, String code){

        ExecuteRequest executeRequest = new ExecuteRequest();
        ClientRequestDto clientRequestDto = createClientRequest(customerResponseDto);
        executeRequest.setNameApi(NameApi.CREATE_CLIENT);
        executeRequest.setBanqueCode(code);
        executeRequest.setRequestBody(clientRequestDto);

        return executeRequest;


    }









    private static ClientRequestDto createClientRequest(CustomerResponseDto customerResponseDto){

        ClientRequestDto clientRequestDto = new ClientRequestDto();
        clientRequestDto.setClientUuid(customerResponseDto.getUuid());
        clientRequestDto.setCity(customerResponseDto.getCity());
        clientRequestDto.setCin(customerResponseDto.getCin());
        clientRequestDto.setEmail(customerResponseDto.getEmail());
        clientRequestDto.setFirstName(customerResponseDto.getFirstName());
        clientRequestDto.setLastName(customerResponseDto.getLastName());
        clientRequestDto.setAddress(customerResponseDto.getAddress());
        clientRequestDto.setPhone(customerResponseDto.getPhone());
        clientRequestDto.setPassword(customerResponseDto.getPassword());
        clientRequestDto.setBirthDate(customerResponseDto.getBirthDate());

        return clientRequestDto;

    }

    public static CompteRequestDto createCompteRequest(String bankUuid, String clientUuid, TypeCompte typeCompte){

        CompteRequestDto compteRequestDto = new CompteRequestDto();
        compteRequestDto.setTypeCompte(typeCompte);
        compteRequestDto.setBanqueUuid(bankUuid);
        compteRequestDto.setCustomerUuid(clientUuid);

        return compteRequestDto;

    }


}
