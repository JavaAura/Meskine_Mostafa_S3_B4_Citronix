package com.brief.citronix.model.DTO.response;

import java.time.LocalDate;
import java.util.List;

public record TreeResponseDTO(
        Long id,
        LocalDate plantingDate,
        Long fieldId,
        List<Long> harvestDetailIds
) {}