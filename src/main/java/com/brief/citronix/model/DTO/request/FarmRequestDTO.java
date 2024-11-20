package com.brief.citronix.model.DTO.request;

import java.time.LocalDate;

public record FarmRequestDTO(
        String name,
        String location,
        double area,
        LocalDate creationDate
) {}