package com.brief.citronix.model.mapper;

import com.brief.citronix.model.DTO.request.HarvestDetailRequestDTO;
import com.brief.citronix.model.DTO.response.HarvestDetailResponseDTO;
import com.brief.citronix.model.entity.Harvest;
import com.brief.citronix.model.entity.HarvestDetail;
import com.brief.citronix.model.entity.Tree;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface HarvestDetailMapper {

    @Mapping(target = "treeId", source = "tree.id")
    @Mapping(target = "harvestId", source = "harvest.id")
    HarvestDetailResponseDTO toDto(HarvestDetail harvestDetail);

    @Mapping(target = "tree", source = "treeId", qualifiedByName = "mapTree")
    @Mapping(target = "harvest", source = "harvestId", qualifiedByName = "mapHarvest")
    HarvestDetail toEntity(HarvestDetailRequestDTO harvestDetailDTO);

    @Named("mapTree")
    default Tree mapTree(Long id) {
        if (id == null) return null;
        Tree tree = new Tree();
        tree.setId(id);
        return tree;
    }

    @Named("mapHarvest")
    default Harvest mapHarvest(Long id) {
        if (id == null) return null;
        Harvest harvest = new Harvest();
        harvest.setId(id);
        return harvest;
    }
}