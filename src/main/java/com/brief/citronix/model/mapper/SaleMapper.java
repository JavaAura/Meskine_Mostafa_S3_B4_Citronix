package com.brief.citronix.model.mapper;

import com.brief.citronix.model.DTO.request.SaleRequestDTO;
import com.brief.citronix.model.DTO.response.SaleResponseDTO;
import com.brief.citronix.model.entity.Harvest;
import com.brief.citronix.model.entity.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface SaleMapper {

    @Mapping(target = "harvestId", source = "harvest.id")
    SaleResponseDTO toDto(Sale sale);

    @Mapping(target = "harvest", source = "harvestId", qualifiedByName = "mapHarvest")
    Sale toEntity(SaleRequestDTO saleDTO);

    @Named("mapHarvest")
    default Harvest mapHarvest(Long id) {
        if (id == null) return null;
        Harvest harvest = new Harvest();
        harvest.setId(id);
        return harvest;
    }
}
