package com.brief.citronix.service.Interface;

import com.brief.citronix.model.DTO.request.HarvestRequestDTO;
import com.brief.citronix.model.DTO.response.HarvestResponseDTO;

import java.util.List;

public interface HarvestService {
    HarvestResponseDTO createHarvest(HarvestRequestDTO harvestRequestDTO);

    HarvestResponseDTO getHarvestById(Long id);

    List<HarvestResponseDTO> getAllHarvests();

    HarvestResponseDTO updateHarvest(Long id, HarvestRequestDTO harvestRequestDTO);

    void deleteHarvest(Long id);
}