package com.brief.citronix.model.DTO.response;

import java.time.LocalDate;
import java.util.List;


public record FarmResponseDTO(
        Long id,
        String name,
        String location,
        double area,
        LocalDate creationDate,
        List<Long> fieldIds
) {}