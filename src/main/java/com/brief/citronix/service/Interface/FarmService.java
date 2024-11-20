package com.brief.citronix.service.Interface;

import com.brief.citronix.model.DTO.request.FarmRequestDTO;
import com.brief.citronix.model.DTO.response.FarmResponseDTO;

import java.util.List;

public interface FarmService {
    FarmResponseDTO createFarm(FarmRequestDTO farmRequestDTO);

    FarmResponseDTO getFarmById(Long id);

    List<FarmResponseDTO> getAllFarms();

    FarmResponseDTO updateFarm(Long id, FarmRequestDTO farmRequestDTO);

    void deleteFarm(Long id);
}
