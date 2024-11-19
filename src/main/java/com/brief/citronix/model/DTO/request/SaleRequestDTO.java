package com.brief.citronix.model.DTO.request;

import java.time.LocalDate;

public record SaleRequestDTO(
        LocalDate saleDate,
        double unitPrice,
        double quantity,
        String client,
        Long harvestId
) {
}
