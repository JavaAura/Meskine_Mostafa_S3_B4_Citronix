package com.brief.citronix.model.DTO.request;

import jakarta.validation.constraints.*;

public record HarvestDetailRequestDTO(
        @NotNull(message = "Unit quantity is required.")
        @Positive(message = "Unit quantity must be greater than zero.")
        double unitQuantity,

        @NotNull(message = "Tree ID is required.")
        @Positive(message = "Tree ID must be a positive number.")
        Long treeId,

        @NotNull(message = "Harvest ID is required.")
        @Positive(message = "Harvest ID must be a positive number.")
        Long harvestId
) {}