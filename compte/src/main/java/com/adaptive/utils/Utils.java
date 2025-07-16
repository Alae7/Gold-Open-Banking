package com.adaptive.utils;


import com.adaptive.model.RIB;

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


}
