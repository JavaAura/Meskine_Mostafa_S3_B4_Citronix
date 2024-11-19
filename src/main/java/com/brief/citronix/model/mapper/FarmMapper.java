package com.brief.citronix.model.mapper;

import com.brief.citronix.model.DTO.request.FarmRequestDTO;
import com.brief.citronix.model.DTO.response.FarmResponseDTO;
import com.brief.citronix.model.entity.Farm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {FieldMapper.class})
public interface FarmMapper {
    @Mapping(target = "fields", source = "fields")
    FarmResponseDTO toDto(Farm farm);

    @Mapping(target = "fields", source = "fields")
    Farm toEntity(FarmRequestDTO farmDTO);
}


