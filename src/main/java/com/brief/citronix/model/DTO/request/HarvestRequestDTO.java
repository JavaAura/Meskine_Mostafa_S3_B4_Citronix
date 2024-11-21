package com.brief.citronix.model.DTO.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record HarvestRequestDTO(
        @NotNull(message = "Harvest date must not be null.")
        @FutureOrPresent(message = "Harvest date cannot be in the past.")
        LocalDate date
) {}