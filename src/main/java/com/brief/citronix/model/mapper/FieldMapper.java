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

    // Mapping from Field entity to FieldResponseDTO
    @Mapping(target = "farmId", source = "farm.id") // Map farm.id to farmId in DTO
    @Mapping(target = "treeIds", source = "trees", qualifiedByName = "mapTrees") // Map trees to treeIds
    FieldResponseDTO toDto(Field field);

    // Mapping from FieldRequestDTO to Field entity
    @Mapping(target = "farm", source = "farmId", qualifiedByName = "mapFarm") // Map farmId to Farm entity
    @Mapping(target = "trees", ignore = true) // Ignore trees mapping if not needed
    Field toEntity(FieldRequestDTO fieldDTO);

    // Convert farmId to Farm entity
    @Named("mapFarm")
    default Farm mapFarm(Long id) {
        if (id == null) return null;
        Farm farm = new Farm();
        farm.setId(id);
        return farm;
    }

    // Convert List<Tree> to List<Long> (treeIds)
    @Named("mapTrees")
    default List<Long> mapTrees(List<Tree> trees) {
        return trees == null ? null : trees.stream().map(Tree::getId).toList();
    }

//    // Convert List<Long> (treeIds) to List<Tree> entities
//    @Named("mapTreesToEntities")
//    default List<Tree> mapTreesToEntities(List<Long> treeIds) {
//        if (treeIds == null) return null;
//        return treeIds.stream().map(id -> {
//            Tree tree = new Tree();
//            tree.setId(id); // Set ID or fetch from the repository
//            return tree;
//        }).toList();
//    }
}