package com.adaptive.service;


import com.adaptive.config.ExecuteRequest;
import com.adaptive.dto.CompteRequestDto;
import com.adaptive.dto.CompteResponseDto;
import com.adaptive.dto.Notification_CompteRequestDto;
import com.adaptive.entity.Compte;
import com.adaptive.entity.StatusCompte;
import com.adaptive.mapper.CompteMapper;
import com.adaptive.model.BanqueResponseDto;
import com.adaptive.model.RIB;
import com.adaptive.model.Transaction;
import com.adaptive.openFeinController.BanqueFeinClient;
import com.adaptive.repository.CompteRepository;
import com.adaptive.utils.Utils;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CompteServiceImpl implements CompteService {

    @Autowired
    private KafkaTemplate<String, Notification_CompteRequestDto> kafkaTemplate;

    @Autowired
    private BanqueFeinClient banqueFeinClient;

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private CompteMapper compteMapper;

    @Autowired Utils utils;

    @Override
    public CompteResponseDto findByRib(Long rib) {

        Compte compte = compteRepository.findByRib(rib);
        compte.setSolde(utils.getAccount(compte));
        compteRepository.save(compte);

        return compteMapper.toResponseDto(compte);
    }

    @Override
    public List<CompteResponseDto> findByBanqueUuid(String banqueUuid) {
        return compteMapper.toResponseDtoList(compteRepository.findByBanqueUuid(banqueUuid));
    }

    @Override
    public boolean create(CompteRequestDto compteRequestDto) {

        try {

            BanqueResponseDto banque = banqueFeinClient.findByUuid(compteRequestDto.getBanqueUuid());
            Compte compte = compteMapper.toEntity(compteRequestDto);
            ExecuteRequest executeRequest = utils.createExecuteRequest(compte);
            ResponseEntity<?> account = banqueFeinClient.execute(executeRequest);
            compte.setRib((Long) account.getBody());
            compteRepository.save(compte);
            Notification_CompteRequestDto notificationRequestDtos = Utils.createNotificationRequestDto(compte);
            kafkaTemplate.send("compte_topic", notificationRequestDtos.getNotificationType() , notificationRequestDtos );




            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }

    }



    @Override
    public String update(Long rib , Transaction transaction) {

        Compte compte = compteRepository.findByRib(rib);

        if (compte != null) {

            ExecuteRequest executeRequest = utils.updateExecuteRequest(compte,transaction.getAmount());
            ResponseEntity<?> status = banqueFeinClient.execute(executeRequest);
            if((boolean) status.getBody()) {
                if (transaction.getAmount() > 0) {
                    if (Utils.detectAnomaly(compte.getTypeCompte().toString(), transaction.getAmount(), transaction.getCreateDateTime().toLocalDate(), transaction.getTransactionType().toUpperCase())) {
                        // anomaly here
                        compte.setSolde(compte.getSolde() + transaction.getAmount());
                        compte.setStatut(StatusCompte.DEACTIVATE);
                        compteRepository.save(compte);
                        Notification_CompteRequestDto notificationRequestDtos = Utils.deactivateNotificationRequestDto(compte);
                        kafkaTemplate.send("anomaly_topic", notificationRequestDtos.getNotificationType(), notificationRequestDtos);
                        return "success";
                    } else {

                        compte.setSolde(compte.getSolde() + transaction.getAmount());
                        compteRepository.save(compte);
                        return "success";

                    }
                } else {
                    compte.setSolde(compte.getSolde() + transaction.getAmount());
                    compteRepository.save(compte);
                    return "success";
                }
            }else {
                return "failure";
            }
        }
        return " compte not found or Deactivated";
    }

    @Override
    public String activate(Long rib) {

        Compte compte = compteRepository.findByRib(rib);
        String message ;

        if (compte != null) {

            compte.setStatut(StatusCompte.ACTIVATE);
            compteRepository.save(compte);

            Notification_CompteRequestDto notificationRequestDtos = Utils.activateNotificationRequestDto(compte);
            kafkaTemplate.send("compte_topic", notificationRequestDtos.getNotificationType() , notificationRequestDtos );

            message = "success";

        }else{

            message = "compte not found ";

        }
        return message;
    }

    @Override
    public String deactivate(Long rib) {

        Compte compte = compteRepository.findByRib(rib);
        String message ;

        if (compte != null) {

            compte.setStatut(StatusCompte.DEACTIVATE);
            compteRepository.save(compte);

            Notification_CompteRequestDto notificationRequestDtos = Utils.deactivateNotificationRequestDto(compte);
            kafkaTemplate.send("compte_topic", notificationRequestDtos.getNotificationType() , notificationRequestDtos );
            message = "success";

        }else {

            message = "compte not found ";
        }

        return message;
    }

    @Override
    public String findCustomerUuidByRib(Long rib) {
        return compteRepository.findCustomerUuidByRib(rib);
    }

    @Override
    public String delete(Long rib) {
        Compte compte = compteRepository.findByRib(rib);
        compte.setDeleted(true);
        compteRepository.save(compte);
        return " successfully deleted ";
    }

}
