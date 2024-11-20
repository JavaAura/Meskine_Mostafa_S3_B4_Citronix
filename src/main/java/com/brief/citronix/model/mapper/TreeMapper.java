package com.brief.citronix.model.mapper;

import com.brief.citronix.model.DTO.request.TreeRequestDTO;
import com.brief.citronix.model.DTO.response.TreeResponseDTO;
import com.brief.citronix.model.entity.Field;
import com.brief.citronix.model.entity.HarvestDetail;
import com.brief.citronix.model.entity.Tree;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TreeMapper {

    @Mapping(target = "fieldId", source = "field.id")
    @Mapping(target = "harvestDetailIds", source = "harvestDetails")
    TreeResponseDTO toDto(Tree tree);

    @Mapping(target = "field", source = "fieldId", qualifiedByName = "mapField")
    @Mapping(target = "harvestDetails", ignore = true)
    Tree toEntity(TreeRequestDTO treeDTO);

    @Named("mapField")
    default Field mapField(Long id) {
        if (id == null) {
            // Optional: Log a warning or handle validation if needed
            return null;  // or throw new IllegalArgumentException("Field ID cannot be null");
        }
        Field field = new Field();
        field.setId(id);
        return field;
    }

    default List<Long> mapHarvestDetails(List<HarvestDetail> harvestDetails) {
        if (harvestDetails == null) return null;
        return harvestDetails.stream().map(HarvestDetail::getId).toList();
    }
}
