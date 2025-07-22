package com.adaptive.service;


import com.adaptive.dto.CompteRequestDto;
import com.adaptive.dto.CompteResponseDto;
import com.adaptive.dto.Notification_CompteRequestDto;
import com.adaptive.entity.Compte;
import com.adaptive.mapper.CompteMapper;
import com.adaptive.model.Banque;
import com.adaptive.model.RIB;
import com.adaptive.model.Transaction;
import com.adaptive.openFeinController.BanqueFeinClient;
import com.adaptive.repository.CompteRepository;
import com.adaptive.utils.Utils;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public CompteResponseDto findByRib(Long rib) {
        return compteMapper.toResponseDto(compteRepository.findByRib(rib));
    }

    @Override
    public List<CompteResponseDto> findByBanqueUuid(String banqueUuid) {
        return compteMapper.toResponseDtoList(compteRepository.findByBanqueUuid(banqueUuid));
    }

    @Override
    public CompteResponseDto create(CompteRequestDto compteRequestDto) {

        Banque banque = banqueFeinClient.getBanqueModel(compteRequestDto.getBanqueUuid());
        Compte compte = compteMapper.toEntity(compteRequestDto);

        compte.setNumCompte(generateNumerousCompte());
        RIB  rib = Utils.generateRib(compte.getNumCompte(),banque.getCodeBanque());
        compte.setRib(rib.getRib());
        compte.setCle(rib.getCle());
        compteRepository.save(compte);

        Notification_CompteRequestDto notificationRequestDtos = Utils.createNotificationRequestDto(compte);
        kafkaTemplate.send("compte_topic", notificationRequestDtos.getNotificationType() , notificationRequestDtos );

        return compteMapper.toResponseDto(compte);
    }



    @Override
    public String update(Long rib , Transaction transaction) {

        Compte compte = compteRepository.findByRib(rib);
        if (compte != null) {
            if ( transaction.getAmount() > 0){
                if (Utils.detectAnomaly(compte.getTypeCompte(),transaction.getAmount(), transaction.getCreateDateTime().toLocalDate(),transaction.getTransactionType().toUpperCase())){
                        // anomaly here
                    compte.setSolde(compte.getSolde() + transaction.getAmount());
                    compte.setStatut("DEACTIVATE");
                    compteRepository.save(compte);

                    return "success";
                }
                else{

                    compte.setSolde(compte.getSolde() + transaction.getAmount());
                    compteRepository.save(compte);
                    return "success";

                }
            }else {

                compte.setSolde(compte.getSolde() + transaction.getAmount());
                compteRepository.save(compte);
                return  "success";

            }
        }

        return " compte not found ";
    }

    @Override
    public String activate(Long rib) {

        Compte compte = compteRepository.findByRib(rib);
        String message ;

        if (compte != null) {

            compte.setStatut("ACTIVATE");
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

            compte.setStatut("DEACTIVATE");
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






    private Long generateNumerousCompte() {

        Long numCompte ;
        do {
            numCompte = Utils.generatorNumerousCompte();
        }while (compteRepository.existsByNumCompte(numCompte));

        return numCompte;

    }


}
