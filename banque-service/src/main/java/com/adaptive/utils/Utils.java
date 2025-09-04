package com.adaptive.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Component
public class Utils {

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<?> postExecution(String url, Object requestBody){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Wrap body + headers into an HttpEntity
        HttpEntity<Object> entity  = new HttpEntity<>(requestBody, headers);

        try {
            return restTemplate.postForEntity(url, entity, String.class);
        }
        catch (HttpStatusCodeException ex){

            System.out.println("POST request failed: " + ex.getStatusCode() + " - " + ex.getResponseBodyAsString());
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
        }
        catch (Exception ex){
            System.out.println("Error occurred: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());

        }
    }
    public ResponseEntity<?> putExecution(String url,Map<String, String> pathParams ,Object requestBody){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Build the final URL with path parameters
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url);
        String finalUrl = uriBuilder.buildAndExpand(pathParams).toUriString();

        // Create the request entity
        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);

        try {
            return restTemplate.exchange(finalUrl, HttpMethod.PUT, entity, String.class);
        }
        catch (HttpStatusCodeException ex) {
            // Log error response body if needed
            System.err.println("PUT request failed: " + ex.getResponseBodyAsString());
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
        }
        catch (Exception ex) {
            // Handle other exceptions
            System.err.println("Unexpected error: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }


    }

    public ResponseEntity<?> deleteExecution(String url,Map<String, String> pathParams){

        try {

            // Build the final URL with path parameters
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url);
            String finalUrl = uriBuilder.buildAndExpand(pathParams).toUriString();

            // Create an empty HttpEntity (no headers, no body)
            HttpEntity<?> entity = HttpEntity.EMPTY;

            return restTemplate.exchange(finalUrl, HttpMethod.DELETE, entity, String.class);

        } catch (HttpStatusCodeException ex) {
            System.err.println("DELETE request failed: " + ex.getResponseBodyAsString());
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());

        } catch (Exception ex) {
            System.err.println("Unexpected error: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

    }

    public ResponseEntity<?> getExecution(String url,Map<String, String> pathParams){

        try {
            // Build the final URL with path parameters using buildAndExpand
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url);
            String finalUrl = uriBuilder.buildAndExpand(pathParams).toUriString();

            // Execute GET request
            ResponseEntity<String> response = restTemplate.getForEntity(finalUrl, String.class);

            // Try to parse response as JSON (List or Map)
            ObjectMapper mapper = new ObjectMapper();
            String body = response.getBody();
            try {
                List<?> list = mapper.readValue(body, List.class);
                return ResponseEntity.ok(list);
            } catch (JsonProcessingException e) {
                Map<?, ?> obj = mapper.readValue(body, Map.class);
                return ResponseEntity.ok(obj);
            }

        } catch (HttpStatusCodeException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

    }

    public ResponseEntity<?> getExecution(String url){

        try {

            // Execute GET request
            return restTemplate.getForEntity(url, String.class);

        } catch (HttpStatusCodeException ex) {
            // Handle HTTP errors
            System.err.println("GET request failed: " + ex.getResponseBodyAsString());
            return ResponseEntity.status(ex.getStatusCode())
                    .body(ex.getResponseBodyAsString());
        } catch (Exception ex) {
            // Handle other exceptions
            System.err.println("Unexpected error: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ex.getMessage());
        }

    }


}
