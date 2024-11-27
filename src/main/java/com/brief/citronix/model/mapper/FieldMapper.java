package com.brief.citronix.model.mapper;

import com.brief.citronix.model.DTO.request.FieldRequestDTO;
import com.brief.citronix.model.DTO.response.FieldResponseDTO;
import com.brief.citronix.model.entity.Field;
import com.brief.citronix.model.entity.Farm;
import com.brief.citronix.model.entity.Tree;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FieldMapper {
    @Mapping(target = "farmId", source = "farm.id")
    @Mapping(target = "treeIds", source = "trees", qualifiedByName = "mapTrees")
    FieldResponseDTO toDto(Field field);

    @Mapping(target = "farm", source = "farmId", qualifiedByName = "mapFarm")
    @Mapping(target = "trees", ignore = true)
    Field toEntity(FieldRequestDTO fieldDTO);

    @Named("mapFarm")
    default Farm mapFarm(Long id) {
        if (id == null) return null;
        Farm farm = new Farm();
        farm.setId(id);
        return farm;
    }

    @Named("mapTrees")
    default List<Long> mapTrees(List<Tree> trees) {
        return trees == null ? null : trees.stream().map(Tree::getId).toList();
    }
}