package com.brief.citronix.model.DTO.request;

import java.time.LocalDate;

public record TreeRequestDTO(
        LocalDate plantingDate,
        Long fieldId
) {}