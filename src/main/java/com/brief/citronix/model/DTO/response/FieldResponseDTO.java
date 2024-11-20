package com.brief.citronix.model.DTO.response;

import java.util.List;

public record FieldResponseDTO(
        Long id,
        double area,
        Long farmId,
        List<Long> treeIds
) {}