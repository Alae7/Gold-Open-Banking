package com.adaptive.utils;

import com.adaptive.dto.CreditRequestDto;
import com.adaptive.entity.Credit;
import com.adaptive.entity.CreditStatus;
import com.adaptive.entity.Echeance;
import com.adaptive.model.CompteResponseDto;
import com.adaptive.model.ProductResponseDto;
import com.adaptive.openFeinController.CompteFeinClient;
import com.adaptive.openFeinController.ProductFeinClient;
import com.adaptive.repository.EcheanceRepository;
import com.adaptive.service.EcheanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Component
public class Utils {

    @Autowired
    private ProductFeinClient productFeinClient;

    @Autowired
    private EcheanceRepository echeanceRepository;

    @Autowired
    private CompteFeinClient comptefeinClient;

    public static String getHashedUuid(Instant dateCreation, Long id) {
        UUID uuid = UUID.randomUUID(); // Generate a random UUID
        String hashString = uuid + dateCreation.toString() + id; // Combine UUID, timestamp, and ID
        byte[] hashBytes = sha256(hashString.getBytes(StandardCharsets.UTF_8)); // Hash the combined string
        return bytesToHex(hashBytes); // Convert bytes to hex representation
    }

    /**
     * Performs SHA-256 hashing on the given byte array.
     *
     * @param input The byte array to hash.
     * @return A byte array containing the SHA-256 hashed result.
     * @throws IllegalStateException If the SHA-256 algorithm is not available.
     */
    private static byte[] sha256(byte[] input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(input);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm is not available", e);
        }
    }

    /**
     * Converts an array of bytes into a hexadecimal string.
     *
     * @param bytes The byte array to convert.
     * @return A hexadecimal string representing the given byte array.
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            result.append(Character.forDigit((b >> 4) & 0xF, 16)) // Convert the higher 4 bits
                    .append(Character.forDigit(b & 0xF, 16));       // Convert the lower 4 bits
        }
        return result.toString();
    }



    public void createEcheance(LocalDate dateDebut, int dure, double remboursement ,String creditUuid,Long rib) {

        for (int i = 1; i <= dure; i++) {

            LocalDate dateRemboursement = dateDebut.plusMonths(i);
            Echeance  e = new Echeance();
            e.setCreditUuid(creditUuid);
            e.setDateEcheance(dateRemboursement);
            e.setMontant(remboursement);
            e.setNumero(i);
            e.setPaye(false);
            e.setRib(rib);
            echeanceRepository.save(e);

        }

    }




     public Credit create(CreditRequestDto creditRequestDto) {

        Credit credit = new Credit();
        ProductResponseDto productResponseDto = productFeinClient.findByUuid(creditRequestDto.getProductUuid());
        credit.setStatus(CreditStatus.DEMANDE);
        credit.setCompteRib(creditRequestDto.getCompteRib());
        credit.setDateDebut(LocalDate.now());
        credit.setDateFin(credit.getDateDebut().plusMonths(productResponseDto.getDuree()));
        credit.setDuree(productResponseDto.getDuree());
        credit.setMontantDemande(productResponseDto.getMontantDemande());
        credit.setRemboursement(productResponseDto.getRemboursement());

        return credit;
    }


    public boolean verifierSold(Double amount, Long rib){

        CompteResponseDto compteResponseDto = comptefeinClient.findByRib(rib);
        double verifier = compteResponseDto.getSolde() -  amount;
        return verifier >= 2000;

    }




}
