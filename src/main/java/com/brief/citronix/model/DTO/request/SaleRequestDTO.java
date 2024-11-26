package com.brief.citronix.model.DTO.request;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record SaleRequestDTO(
        @NotNull(message = "Sale date is required.")
        @PastOrPresent(message = "Sale date cannot be in the future.")
        LocalDate saleDate,

        @NotNull(message = "Unit price is required.")
        @Positive(message = "Unit price must be greater than zero.")
        double unitPrice,

        @NotBlank(message = "Client name is required.")
        @Size(max = 100, message = "Client name cannot exceed 100 characters.")
        String client,

        @NotNull(message = "Quantity is required.")
        @Positive(message = "Quantity must be greater than zero.")
        double quantity,

        @NotNull(message = "Harvest ID is required.")
        @Positive(message = "Harvest ID must be a positive number.")
        Long harvestId
) {}