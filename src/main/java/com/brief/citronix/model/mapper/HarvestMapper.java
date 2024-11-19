package com.brief.citronix.model.mapper;

import com.brief.citronix.model.DTO.request.HarvestRequestDTO;
import com.brief.citronix.model.DTO.response.HarvestResponseDTO;
import com.brief.citronix.model.entity.Harvest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HarvestMapper {
    HarvestResponseDTO toDto(Harvest harvest);

    Harvest toEntity(HarvestRequestDTO harvestDTO);
}
