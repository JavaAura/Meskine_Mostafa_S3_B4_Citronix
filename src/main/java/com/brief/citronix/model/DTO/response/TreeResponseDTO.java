package com.brief.citronix.model.DTO.response;

import java.time.LocalDate;

public record TreeResponseDTO(
        Long id,
        LocalDate plantingDate,
        int age,
        Long fieldId
) {
}
