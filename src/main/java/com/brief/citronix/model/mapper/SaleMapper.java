package com.brief.citronix.model.mapper;

import com.brief.citronix.model.DTO.request.SaleRequestDTO;
import com.brief.citronix.model.DTO.response.SaleResponseDTO;
import com.brief.citronix.model.entity.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleMapper {
    @Mapping(source = "harvest.id", target = "harvestId")
    @Mapping(target = "revenue", expression = "java(sale.getUnitPrice() * sale.getQuantity())")
    SaleResponseDTO toDto(Sale sale);

    @Mapping(source = "harvestId", target = "harvest.id")
    Sale toEntity(SaleRequestDTO saleDTO);
}
