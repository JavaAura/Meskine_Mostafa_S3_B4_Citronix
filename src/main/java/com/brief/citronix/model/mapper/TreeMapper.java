package com.brief.citronix.model.mapper;

import com.brief.citronix.model.DTO.request.TreeRequestDTO;
import com.brief.citronix.model.DTO.response.TreeResponseDTO;
import com.brief.citronix.model.entity.Tree;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TreeMapper {
    @Mapping(source = "field.id", target = "fieldId")
    TreeResponseDTO toDto(Tree tree);

    @Mapping(source = "fieldId", target = "field.id")
    Tree toEntity(TreeRequestDTO treeDTO);
}
