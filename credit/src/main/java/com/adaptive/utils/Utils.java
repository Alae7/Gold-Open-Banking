package com.adaptive.utils;

import com.adaptive.config.ExecuteRequest;
import com.adaptive.dto.CreditMifosRequestDto;
import com.adaptive.dto.CreditRequestDto;
import com.adaptive.entity.Credit;
import com.adaptive.entity.CreditStatus;
import com.adaptive.entity.Echeance;
import com.adaptive.entity.NameApi;
import com.adaptive.model.AmlCredit;
import com.adaptive.model.CnssRequestDto;
import com.adaptive.model.GdiRequestDto;
import com.adaptive.model.ProductResponseDtoV1;
import com.adaptive.openFeinController.ProductFeinClient;
import com.adaptive.repository.EcheanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class Utils {

    @Autowired
    private ProductFeinClient productFeinClient;

    @Autowired
    private EcheanceRepository echeanceRepository;

    @Autowired
    RestTemplate restTemplate;



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



    public void createEcheance(Credit credit) {

        int k = 1 ;
        if(credit.getFrequency() == 4){
            k= 4;
        }

        for (int i = 1; i <= credit.getDuree();i = i + k) {

            LocalDate dateRemboursement = credit.getDateDebut().plusMonths(i);
            Echeance  e = new Echeance();
            e.setCredit(credit);
            e.setDateEcheance(dateRemboursement);
            e.setMontant(credit.getRemboursement());
            e.setNumero(i);
            e.setPaye(false);
            e.setRib(credit.getCompteRib());
            echeanceRepository.save(e);

        }

    }




     public Credit create(CreditRequestDto creditRequestDto) {

        Credit credit = new Credit();
        ProductResponseDtoV1 productResponseDto = productFeinClient.findByUuidV1(creditRequestDto.getProductUuid());
        credit.setStatus(CreditStatus.DEMANDE);
        credit.setCompteRib(creditRequestDto.getCompteRib());
        credit.setDateDebut(LocalDate.now());
        credit.setDateFin(credit.getDateDebut().plusMonths(productResponseDto.getDuree()));
        credit.setDuree(productResponseDto.getDuree());
        credit.setMontantDemande(productResponseDto.getMontantDemande());
        credit.setRemboursement(productResponseDto.getRemboursement());
        credit.setFrequency(productResponseDto.getFrequency());
        credit.setCodeBank(productResponseDto.getCodeBank());
        return credit;
    }


    public boolean kycCredit(CreditRequestDto creditRequestDto) {

        switch (creditRequestDto.getTypeCompte()){

            case SELF_EMPLOYED,ENTREPRISE -> {

                GdiRequestDto gdiRequestDto = getGdiRequestDto(creditRequestDto);
                String url = "http://localhost:4998/api/dgi";
                return Boolean.TRUE.equals(restTemplate.postForObject(url, gdiRequestDto, Boolean.class));

            }

            case FONCTIONNAIRE,PROFESSIONNEL -> {

                CnssRequestDto cnssRequestDto = getCnssRequestDto(creditRequestDto);
                String url = "http://localhost:4999/api/cnss";
                return Boolean.TRUE.equals(restTemplate.postForObject(url, cnssRequestDto, Boolean.class));

            }
            default -> {

                return false;

            }

        }
    }

    public boolean amlCredit(CreditRequestDto creditRequestDto , Double amount) {

        String url = "http://localhost:8000/predict";

        AmlCredit amlCredit = new AmlCredit();
        amlCredit.setPerson_age(creditRequestDto.getAge());
        amlCredit.setPerson_income(creditRequestDto.getSalaire());
        amlCredit.setLoan_amnt(amount);
        amlCredit.setPerson_home_ownership(creditRequestDto.getHome());
        amlCredit.setCb_person_default_on_file(creditRequestDto.getHasCredit());
        amlCredit.setLoan_intent(creditRequestDto.getTypeCredit());
        amlCredit.setPerson_emp_length(creditRequestDto.getAnciennete());
        Map<String, Object> response = restTemplate.postForObject(url, amlCredit, Map.class);
        return Boolean.TRUE.equals(response != null ? response.get("loan_status") : false);

    }



    private static CnssRequestDto  getCnssRequestDto(CreditRequestDto creditRequestDto) {

        CnssRequestDto cnssRequestDto = new CnssRequestDto();

        cnssRequestDto.setCin(creditRequestDto.getCin());
        cnssRequestDto.setSalaire(creditRequestDto.getSalaire());
        cnssRequestDto.setFirstName(creditRequestDto.getFirstName());
        cnssRequestDto.setLastNam(creditRequestDto.getLastNam());
        cnssRequestDto.setNbrEnfant(creditRequestDto.getNbrEnfant());
        cnssRequestDto.setNbrFemme(creditRequestDto.getNbrFemme());
        cnssRequestDto.setNumInscription(creditRequestDto.getNumInscription());

        return cnssRequestDto ;

    }


    private static GdiRequestDto getGdiRequestDto(CreditRequestDto creditRequestDto) {


        GdiRequestDto gdiRequestDto = new GdiRequestDto();

        gdiRequestDto.setCin(creditRequestDto.getCin());
        gdiRequestDto.setIncome(creditRequestDto.getSalaire());
        gdiRequestDto.setFirstName(creditRequestDto.getFirstName());
        gdiRequestDto.setLastNam(creditRequestDto.getLastNam());
        gdiRequestDto.setNbrEnfant(creditRequestDto.getNbrEnfant());
        gdiRequestDto.setNbrFemme(creditRequestDto.getNbrFemme());
        gdiRequestDto.setNumInscription(creditRequestDto.getNumInscription());

        return gdiRequestDto ;

    }


    public static ExecuteRequest createExecuteRequest(Credit credit) {

        ExecuteRequest executeRequest = new ExecuteRequest();
        CreditMifosRequestDto creditMifosRequestDto = getCreditMifosRequestDto(credit);

        executeRequest.setRequestBody(creditMifosRequestDto);
        executeRequest.setNameApi(NameApi.CREATE_CREDIT);
        executeRequest.setBanqueCode(credit.getCodeBank());

        return executeRequest ;
    }

    private static CreditMifosRequestDto getCreditMifosRequestDto(Credit credit) {

        CreditMifosRequestDto creditMifosRequestDto = new CreditMifosRequestDto();
        creditMifosRequestDto.setCompteRib(credit.getCompteRib());
        creditMifosRequestDto.setTypeCredit(credit.getTypeCredit());
        creditMifosRequestDto.setDuree(credit.getDuree());
        creditMifosRequestDto.setEcheances(credit.getEcheances());
        creditMifosRequestDto.setRemboursement(credit.getRemboursement());
        creditMifosRequestDto.setStatus(credit.getStatus());
        creditMifosRequestDto.setDateDebut(credit.getDateDebut());
        creditMifosRequestDto.setDateFin(credit.getDateFin());
        creditMifosRequestDto.setUuid(credit.getUuid());
        creditMifosRequestDto.setMontantDemande(credit.getMontantDemande());
        creditMifosRequestDto.setTauxInteret(credit.getTauxInteret());
        creditMifosRequestDto.setTypeCompte(credit.getTypeCompte());

        return creditMifosRequestDto;

    }


}
