package com.brief.citronix.model.DTO.response;

import com.brief.citronix.model.enums.Season;

import java.time.LocalDate;
import java.util.List;

public record HarvestResponseDTO(
        Long id,
        LocalDate date,
        Season season,
        double totalQuantity,
        List<Long> saleIds,
        List<Long> harvestDetailIds
) {}