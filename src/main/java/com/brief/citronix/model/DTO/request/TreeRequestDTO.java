package com.brief.citronix.model.DTO.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record TreeRequestDTO(
        @NotNull(message = "Planting date must not be null.")
        @PastOrPresent(message = "Planting date cannot be in the future.")
        LocalDate plantingDate,

        @NotNull(message = "Field ID must not be null.")
        Long fieldId
) {}