package com.adaptive.utils;


import com.adaptive.dto.TransactionRequestDto;
import com.adaptive.entity.Transaction;
import com.adaptive.model.CompteResponseDto;
import com.adaptive.model.NotificationRequestDto;
import com.adaptive.model.Status;
import com.adaptive.openFeinController.CompteFeinClient;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.UUID;

public class Utils {




    private static CompteFeinClient compteFeinClient;


    public static Status doTransaction(TransactionRequestDto transactionRequestDto){

        CompteResponseDto sourceCompte = compteFeinClient.findByRib(transactionRequestDto.getSourceRib());
        CompteResponseDto targetCompte = compteFeinClient.findByRib(transactionRequestDto.getTargetRib());
        if(sourceCompte == null){
            Status status = new Status();
            status.setStatutSourceRib("rib : " + transactionRequestDto.getSourceRib() + " not found");
            return status;
        }else if(targetCompte == null){

            Status status = new Status();
            status.setStatutSourceRib("targetRib : " + transactionRequestDto.getTargetRib() + " not found");
            return status;

        }else if(!verifySold(sourceCompte, transactionRequestDto.getAmount())){

            Status status = new Status();
            status.setStatutSourceRib("REFUSED");
            status.setStatutTargetRib("REFUSED");
            return status;

        }else {
            Status status = new Status();

            status.setStatutSourceRib(compteFeinClient.versement(transactionRequestDto.getSourceRib(), -transactionRequestDto.getAmount()));
            status.setStatutTargetRib(compteFeinClient.versement(transactionRequestDto.getTargetRib(), transactionRequestDto.getAmount()));
            return status;
        }

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

    public static NotificationRequestDto createNotification(Transaction transaction){

        NotificationRequestDto notificationRequestDto = new NotificationRequestDto();
        notificationRequestDto.setNotificationType(transaction.getType());
        notificationRequestDto.setTransactionUuid(transaction.getUuid());

        return notificationRequestDto;

    }



}
