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

    @Mapping(target = "sales", ignore = true) // Optional: Add logic if you need to map back to Sales
    @Mapping(target = "harvestDetails", ignore = true) // Optional: Add logic if you need to map back to HarvestDetails
    Harvest toEntity(HarvestRequestDTO harvestDTO);

    @Named("mapSales")
    default List<Long> mapSales(List<Sale> sales) {
        return sales == null ? null : sales.stream().map(Sale::getId).toList();
    }

    @Named("mapHarvestDetails")
    default List<Long> mapHarvestDetails(List<HarvestDetail> harvestDetails) {
        return harvestDetails == null ? null : harvestDetails.stream().map(HarvestDetail::getId).toList();
    }

    // Optional: If you ever need to map back from IDs to entities
    @Named("mapSalesToEntities")
    default List<Sale> mapSalesToEntities(List<Long> saleIds) {
        if (saleIds == null) return null;
        // Placeholder for logic to retrieve Sale entities by their IDs
        // You can implement this by calling a repository to fetch Sale entities by IDs
        return saleIds.stream().map(id -> {
            Sale sale = new Sale();
            sale.setId(id);
            return sale;
        }).toList();
    }

    @Named("mapHarvestDetailsToEntities")
    default List<HarvestDetail> mapHarvestDetailsToEntities(List<Long> harvestDetailIds) {
        if (harvestDetailIds == null) return null;
        // Placeholder for logic to retrieve HarvestDetail entities by their IDs
        return harvestDetailIds.stream().map(id -> {
            HarvestDetail harvestDetail = new HarvestDetail();
            harvestDetail.setId(id);
            return harvestDetail;
        }).toList();
    }
}
