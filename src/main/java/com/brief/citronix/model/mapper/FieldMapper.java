package com.brief.citronix.model.mapper;

import com.brief.citronix.model.DTO.request.FieldRequestDTO;
import com.brief.citronix.model.DTO.response.FieldResponseDTO;
import com.brief.citronix.model.entity.Field;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FieldMapper {
    @Mapping(source = "farm.id", target = "farmId")
    FieldResponseDTO toDto(Field field);

    @Mapping(source = "farmId", target = "farm.id")
    Field toEntity(FieldRequestDTO fieldDTO);
}