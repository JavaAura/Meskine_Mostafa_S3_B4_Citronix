package com.brief.citronix.model.DTO.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record FarmRequestDTO(
        @NotBlank(message = "Name cannot be blank")
        @Size(max = 100, message = "Name must not exceed 100 characters")
        String name,

        @NotBlank(message = "Location cannot be blank")
        @Size(max = 200, message = "Location must not exceed 200 characters")
        String location,

        @Min(value = 0, message = "Farm area must be a positive value.")
        double area,

        @FutureOrPresent(message = "creation date must be a present or future date")
        @NotNull(message = "Creation date cannot be null")
        LocalDate creationDate
) {}
