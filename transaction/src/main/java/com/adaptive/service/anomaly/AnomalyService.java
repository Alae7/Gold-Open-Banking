package com.adaptive.service.anomaly;

import com.adaptive.dto.AnomalyRequestDto;
import com.adaptive.dto.AnomalyResponseDto;


import java.util.List;

public interface AnomalyService {

    AnomalyResponseDto findByid(Long uuid);

    List<AnomalyResponseDto> findAll();

    List<AnomalyResponseDto> findAllIsTraite(boolean traite);

    boolean create(AnomalyRequestDto anomalyRequestDto);

}
