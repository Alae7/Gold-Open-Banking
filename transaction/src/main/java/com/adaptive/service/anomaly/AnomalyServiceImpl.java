package com.adaptive.service.anomaly;

import com.adaptive.dto.AnomalyRequestDto;
import com.adaptive.dto.AnomalyResponseDto;
import com.adaptive.entity.Anomaly;
import com.adaptive.repository.AnomalyRepository;
import com.adaptive.utils.Utils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AnomalyServiceImpl implements AnomalyService {

    @Autowired
    private AnomalyRepository anomalyRepository;

    @Autowired
    private Utils utils;


    @Override
    public AnomalyResponseDto findByid(Long id) {
        return utils.getAnomaly(anomalyRepository.getAnomalyById(id));
    }

    @Override
    public List<AnomalyResponseDto> findAll() {
        return utils.getAnomaly(anomalyRepository.findAll());
    }

    @Override
    public List<AnomalyResponseDto> findAllIsTraite(boolean traite) {
        return utils.getAnomaly(anomalyRepository.findByTraite(traite));
    }

    @Override
    public boolean create(AnomalyRequestDto anomalyRequestDto) {

        try {
            Anomaly anomaly = Utils.createAnomaly(anomalyRequestDto);
            anomalyRepository.save(anomaly);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
