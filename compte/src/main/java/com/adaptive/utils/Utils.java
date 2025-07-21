package com.adaptive.utils;


import com.adaptive.entity.Compte;
import com.adaptive.model.NotificationRequestDto;
import com.adaptive.model.RIB;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    /**
     * Génère un numéro de compte bancaire (Long à 10 chiffres)
     */
    public static Long generatorNumerousCompte() {

        return ThreadLocalRandom.current().nextLong(1_000_000_000L, 9_999_999_999L);
    }


    /**
     * Génère un RIB basé sur un numéro de compte (sans agence)
     */

    public static RIB generateRib(Long numCompte , String codeBanque) {

        // Format du numéro de compte à 10 chiffres
        String compteStr = String.format("%010d", numCompte);

        // Clé provisoire "00" pour le calcul
        String base = codeBanque + compteStr + "00";

        // Calcul de la clé de contrôle
        long baseLong = Long.parseLong(base);
        int cle = 97 - (int)(baseLong % 97);

        // Construction du RIB complet
        String ribStr = codeBanque + compteStr + String.format("%02d", cle);
        Long numRib = Long.parseLong(ribStr); // Assuré de rester dans la limite de Long

        // Création de l'objet RIB
        RIB rib = new RIB();
        rib.setRib(numRib);
        rib.setCle(cle);

        return rib;
    }



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
     * Crée un objet NotificationRequestDto pour la création de compte.
     *
     * @param compte le compte concerné
     * @return un objet NotificationRequestDto avec type "CREATION"
     */

    public static NotificationRequestDto createNotificationRequestDto(Compte compte){

        NotificationRequestDto notificationRequestDto = new NotificationRequestDto();
        notificationRequestDto.setNotificationType("CREATION");
        notificationRequestDto.setCompteRib(compte.getRib());

        return notificationRequestDto;

    }


    /**
     * Crée un objet NotificationRequestDto pour l'activation de compte.
     *
     * @param compte le compte concerné
     * @return un objet NotificationRequestDto avec type "ACTIVATION"
     */

    public static NotificationRequestDto activateNotificationRequestDto(Compte compte){

        NotificationRequestDto notificationRequestDto = new NotificationRequestDto();
        notificationRequestDto.setNotificationType("ACTIVATION");
        notificationRequestDto.setCompteRib(compte.getRib());

        return notificationRequestDto;

    }


    /**
     * Crée un objet NotificationRequestDto pour la désactivation de compte.
     *
     * @param compte le compte concerné
     * @return un objet NotificationRequestDto avec type "DEACTIVATION"
     */

    public static NotificationRequestDto deactivateNotificationRequestDto(Compte compte){

        NotificationRequestDto notificationRequestDto = new NotificationRequestDto();
        notificationRequestDto.setNotificationType("DEACTIVATION");
        notificationRequestDto.setCompteRib(compte.getRib());

        return notificationRequestDto;
    }


}
