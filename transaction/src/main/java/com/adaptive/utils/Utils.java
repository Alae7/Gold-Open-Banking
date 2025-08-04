package com.adaptive.utils;


import com.adaptive.dto.NotificationRequestDto;
import com.adaptive.dto.TransactionRequestDto;
import com.adaptive.entity.Transaction;
import com.adaptive.model.CompteResponseDto;
import com.adaptive.model.Status;
import com.adaptive.openFeinController.CompteFeinClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.UUID;

@Component
public class Utils {



    @Autowired
    private  CompteFeinClient compteFeinClient;


    public Status doTransaction(TransactionRequestDto transactionRequestDto){

        Status status = new Status();

        CompteResponseDto source = compteFeinClient.findByRib(transactionRequestDto.getSourceRib());

        if (source == null) {
            status.setStatutSourceRib("rib : " + transactionRequestDto.getSourceRib() + " not found");
            return status;
        }

        // Cas de virement
        if (transactionRequestDto.getTargetRib() != null) {
            CompteResponseDto target = compteFeinClient.findByRib(transactionRequestDto.getTargetRib());
            if (target == null) {
                status.setStatutSourceRib("targetRib : " + transactionRequestDto.getTargetRib() + " not found");
                return status;
            }

            if (!verifySold(source, transactionRequestDto.getAmount())) {
                status.setStatutSourceRib("REFUSED");
                status.setStatutTargetRib("REFUSED");
                return status;
            }

            // Débit source, crédit target
            status.setStatutSourceRib(compteFeinClient.versement(transactionRequestDto.getSourceRib(), -transactionRequestDto.getAmount()));
            status.setStatutTargetRib(compteFeinClient.versement(transactionRequestDto.getTargetRib(), transactionRequestDto.getAmount()));
            return status;
        }

        // Cas sans targetRib
        switch (transactionRequestDto.getType()) {
            case RETRAIT -> {
                status.setStatutSourceRib(compteFeinClient.versement(transactionRequestDto.getSourceRib(), -transactionRequestDto.getAmount()));
            }

            case REMBOURSEMENT -> {
                if (verifySoldCredit(source, transactionRequestDto.getAmount())) {
                    status.setStatutSourceRib(compteFeinClient.versement(transactionRequestDto.getSourceRib(), -transactionRequestDto.getAmount()));
                } else {
                    status.setStatutSourceRib("REFUSED");
                }
            }

            case VERSEMENT -> {
                status.setStatutSourceRib(compteFeinClient.versement(transactionRequestDto.getSourceRib(), transactionRequestDto.getAmount()));
            }

            default -> {
                status.setStatutSourceRib("INVALID");
            }
        }

        return status;
    }


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


    private static boolean verifySold(CompteResponseDto compteResponseDto , Double amount){
        return compteResponseDto.getSolde() >= amount;
    }

    private static boolean verifySoldCredit(CompteResponseDto compteResponseDto , Double amount){
        return compteResponseDto.getSolde() - amount >= 2000;
    }

    public static NotificationRequestDto createNotification(Transaction transaction){

        NotificationRequestDto notificationRequestDto = new NotificationRequestDto();
        notificationRequestDto.setNotificationType(transaction.getType().toString());
        notificationRequestDto.setTransactionUuid(transaction.getUuid());

        return notificationRequestDto;

    }



}
