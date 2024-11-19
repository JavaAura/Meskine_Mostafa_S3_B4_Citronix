package com.brief.citronix.model.DTO.response;

public record FarmResponseDTO(
        Long id,
        String name,
        String location,
        double area
) {
}
