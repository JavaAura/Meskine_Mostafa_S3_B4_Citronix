package com.brief.citronix.model.DTO.request;

import com.brief.citronix.model.enums.Season;

import java.time.LocalDate;

public record HarvestRequestDTO(
        LocalDate date,
        Season season,
        double totalQuantity
) {}