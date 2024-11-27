package com.brief.citronix.model.mapper;

import com.brief.citronix.model.DTO.request.FarmRequestDTO;
import com.brief.citronix.model.DTO.response.FarmResponseDTO;
import com.brief.citronix.model.entity.Farm;
import com.brief.citronix.model.entity.Field;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface FarmMapper {

    @Mapping(target = "fieldIds", source = "fields", qualifiedByName = "mapFields")
    FarmResponseDTO toDto(Farm farm);

    @Mapping(target = "fields", ignore = true)
    Farm toEntity(FarmRequestDTO farmDTO);

    @Named("mapFields")
    default List<Long> mapFields(List<Field> fields) {
        return fields == null ? null : fields.stream().map(Field::getId).toList();
    }
}