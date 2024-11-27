package com.brief.citronix.model.DTO.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record FieldRequestDTO(
        @Min(value = 0, message = "Field area must be a positive value.")
        double area,

        @NotNull(message = "Farm ID must not be null.")
        Long farmId
) {}