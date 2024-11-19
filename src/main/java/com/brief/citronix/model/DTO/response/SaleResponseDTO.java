package com.brief.citronix.model.DTO.response;

import java.time.LocalDate;

public record SaleResponseDTO(
        Long id,
        LocalDate saleDate,
        double unitPrice,
        double quantity,
        String client,
        double revenue,
        Long harvestId
) {
}
