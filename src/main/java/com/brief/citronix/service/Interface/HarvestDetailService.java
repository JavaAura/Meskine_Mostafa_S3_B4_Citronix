package com.brief.citronix.service.Interface;

import com.brief.citronix.model.DTO.request.HarvestDetailRequestDTO;
import com.brief.citronix.model.DTO.response.HarvestDetailResponseDTO;

import java.util.List;

public interface HarvestDetailService {
    HarvestDetailResponseDTO createHarvestDetail(HarvestDetailRequestDTO harvestDetailRequestDTO);
    HarvestDetailResponseDTO getHarvestDetailById(Long id);
    List<HarvestDetailResponseDTO> getAllHarvestDetails();
    HarvestDetailResponseDTO updateHarvestDetail(Long id, HarvestDetailRequestDTO harvestDetailRequestDTO);
    void deleteHarvestDetail(Long id);
}

