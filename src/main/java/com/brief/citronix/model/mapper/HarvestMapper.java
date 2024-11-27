package com.brief.citronix.model.mapper;

import com.brief.citronix.model.DTO.request.HarvestRequestDTO;
import com.brief.citronix.model.DTO.response.HarvestResponseDTO;
import com.brief.citronix.model.entity.Harvest;
import com.brief.citronix.model.entity.HarvestDetail;
import com.brief.citronix.model.entity.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HarvestMapper {

    @Mapping(target = "saleIds", source = "sales", qualifiedByName = "mapSales")
    @Mapping(target = "harvestDetailIds", source = "harvestDetails", qualifiedByName = "mapHarvestDetails")
    HarvestResponseDTO toDto(Harvest harvest);

    @Mapping(target = "sales", ignore = true)
    @Mapping(target = "harvestDetails", ignore = true)
    Harvest toEntity(HarvestRequestDTO harvestDTO);

    @Named("mapSales")
    default List<Long> mapSales(List<Sale> sales) {
        return sales == null ? null : sales.stream().map(Sale::getId).toList();
    }

    @Named("mapHarvestDetails")
    default List<Long> mapHarvestDetails(List<HarvestDetail> harvestDetails) {
        return harvestDetails == null ? null : harvestDetails.stream().map(HarvestDetail::getId).toList();
    }
}