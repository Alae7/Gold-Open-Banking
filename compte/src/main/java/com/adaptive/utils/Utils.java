package com.adaptive.utils;


import com.adaptive.config.ExecuteRequest;
import com.adaptive.dto.Notification_CompteRequestDto;
import com.adaptive.entity.Compte;
import com.adaptive.entity.NameApi;
import com.adaptive.model.AccountRequestDto;
import com.adaptive.model.BanqueResponseDto;
import com.adaptive.openFeinController.BanqueFeinClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class Utils {


    @Autowired
    private BanqueFeinClient banqueFeinClient;

    /**
     * Appelle l'API de prédiction d'anomalie.
     *
     * @param accountType le type de compte ("etudiant", "fonctionnaire", etc.)
     * @param amount le montant de la Transaction
     * @param createDateTime date de transaction
     * @return true si l’anomalie est détectée, sinon false
     */

    public static boolean detectAnomaly(String accountType, double amount, LocalDate createDateTime, String transaction_type){

        String url = "http://localhost:5000/predict";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> request = new HashMap<>();
        request.put("accountType", accountType);
        request.put("amount", amount);
        request.put("createDateTime", createDateTime.toString());
        request.put("transaction_type", transaction_type);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Object result = response.getBody().get("anomaly");
                return result != null && Boolean.parseBoolean(result.toString());
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de l'appel à l'API : " + e.getMessage());
        }
        return false;
    }


    /**
     * Crée un objet Notification_CompteRequestDto pour la création de compte.
     *
     * @param compte le compte concerné
     * @return un objet Notification_CompteRequestDto avec type "CREATION"
     */

    public static Notification_CompteRequestDto createNotificationRequestDto(Compte compte){

        Notification_CompteRequestDto notificationRequestDto = new Notification_CompteRequestDto();
        notificationRequestDto.setNotificationType("CREATION");
        notificationRequestDto.setCompteRib(compte.getRib());

        return notificationRequestDto;

    }


    /**
     * Crée un objet Notification_CompteRequestDto pour l'activation de compte.
     *
     * @param compte le compte concerné
     * @return un objet Notification_CompteRequestDto avec type "ACTIVATION"
     */

    public static Notification_CompteRequestDto activateNotificationRequestDto(Compte compte){

        Notification_CompteRequestDto notificationRequestDto = new Notification_CompteRequestDto();
        notificationRequestDto.setNotificationType("ACTIVATION");
        notificationRequestDto.setCompteRib(compte.getRib());

        return notificationRequestDto;

    }


    /**
     * Crée un objet Notification_CompteRequestDto pour la désactivation de compte.
     *
     * @param compte le compte concerné
     * @return un objet Notification_CompteRequestDto avec type "DEACTIVATION"
     */

    public static Notification_CompteRequestDto deactivateNotificationRequestDto(Compte compte){

        Notification_CompteRequestDto notificationRequestDto = new Notification_CompteRequestDto();
        notificationRequestDto.setNotificationType("DEACTIVATION");
        notificationRequestDto.setCompteRib(compte.getRib());

        return notificationRequestDto;
    }


    public ExecuteRequest createExecuteRequest(Compte compte){

        ExecuteRequest executeRequest = new ExecuteRequest();
        BanqueResponseDto banqueResponseDto = banqueFeinClient.findByUuid(compte.getBanqueUuid());
        AccountRequestDto accountRequestDto =  new AccountRequestDto();
        accountRequestDto.setTypeAccount(compte.getTypeCompte());
        accountRequestDto.setClientUuid(compte.getCustomerUuid());
        executeRequest.setNameApi(NameApi.CREATE_ACCOUNT);
        executeRequest.setBanqueCode(banqueResponseDto.getCode());
        executeRequest.setRequestBody(accountRequestDto);

        return executeRequest;

    }


    public ExecuteRequest updateExecuteRequest(Compte compte, double amount) {

        ExecuteRequest executeRequest = new ExecuteRequest();
        BanqueResponseDto banqueResponseDto = banqueFeinClient.findByUuid(compte.getBanqueUuid());
        executeRequest.setNameApi(NameApi.UPDATE_ACCOUNT);
        executeRequest.setBanqueCode(banqueResponseDto.getCode());
        executeRequest.setRequestBody(amount);
        executeRequest.setPathParams(Map.of("rib", String.valueOf(compte.getRib())));
        return executeRequest;

    }

    public Double getAccount(Compte compte) {

        ExecuteRequest executeRequest = new ExecuteRequest();
        BanqueResponseDto banqueResponseDto = banqueFeinClient.findByUuid(compte.getBanqueUuid());
        executeRequest.setNameApi(NameApi.GET_ACCOUNT_BY_RIB);
        executeRequest.setBanqueCode(banqueResponseDto.getCode());
        executeRequest.setPathParams(Map.of("rib", String.valueOf(compte.getRib())));
        ResponseEntity<?> response = banqueFeinClient.execute(executeRequest);

        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
        return (Double) map.get("solde");

    }
}
