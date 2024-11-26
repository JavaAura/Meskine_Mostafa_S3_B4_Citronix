package com.brief.citronix.model.DTO.response;

import java.time.LocalDate;

public record SaleResponseDTO(
        Long id,
        LocalDate saleDate,
        double unitPrice,
        String client,
        double quantity,
        double revenue,
        Long harvestId
) {}