package com.adaptive.utils;


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

        String base = codeBanque + String.format("%010d", numCompte) + "00"; // ajout temporaire de "00" pour calcul clé
        long baseLong = Long.parseLong(base);
        int cle = 97 - (int)(baseLong % 97);
        String numRib = codeBanque + String.format("%010d", numCompte) + String.format("%02d", cle);
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



}
