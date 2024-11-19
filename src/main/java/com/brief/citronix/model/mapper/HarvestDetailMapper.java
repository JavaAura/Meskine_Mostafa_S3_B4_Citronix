package com.brief.citronix.model.mapper;

import com.brief.citronix.model.DTO.request.HarvestDetailRequestDTO;
import com.brief.citronix.model.DTO.response.HarvestDetailResponseDTO;
import com.brief.citronix.model.entity.HarvestDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HarvestDetailMapper {
    @Mapping(source = "tree.id", target = "treeId")
    @Mapping(source = "harvest.id", target = "harvestId")
    HarvestDetailResponseDTO toDto(HarvestDetail harvestDetail);

    @Mapping(source = "treeId", target = "tree.id")
    @Mapping(source = "harvestId", target = "harvest.id")
    HarvestDetail toEntity(HarvestDetailRequestDTO harvestDetailDTO);
}
