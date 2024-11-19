package com.brief.citronix.model.DTO.response;

import com.brief.citronix.model.enums.Season;

import java.time.LocalDate;

public record HarvestResponseDTO(
        Long id,
        LocalDate date,
        Season season,
        double totalQuantity
) {
}
