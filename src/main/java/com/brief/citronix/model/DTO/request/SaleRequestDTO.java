package com.brief.citronix.model.DTO.request;

import java.time.LocalDate;

public record SaleRequestDTO(
        LocalDate saleDate,
        double unitPrice,
        String client,
        double quantity,
        Long harvestId
) {}