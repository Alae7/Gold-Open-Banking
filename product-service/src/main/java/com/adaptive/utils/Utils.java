package com.adaptive.utils;



import com.adaptive.config.ExecuteRequest;
import com.adaptive.dto.ProductResponseDto;
import com.adaptive.entity.NameApi;
import com.adaptive.entity.Product;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public class Utils {

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

    public static ExecuteRequest createExecuteRequest(String uuid , String code) {

        ExecuteRequest executeRequest = new ExecuteRequest();
        executeRequest.setPathParams(Map.of("uuid", uuid));
        executeRequest.setBanqueCode(code);
        executeRequest.setNameApi(NameApi.GET_PRODUCT);
        return executeRequest;

    }

    public static ExecuteRequest createExecuteRequest(ProductResponseDto product) {

        ExecuteRequest executeRequest = new ExecuteRequest();
        executeRequest.setRequestBody(product);
        executeRequest.setNameApi(NameApi.CREATE_PRODUCT);

        return executeRequest;

    }

}
