package com.brief.citronix.model.DTO.response;

public record HarvestDetailResponseDTO(
        Long id,
        double unitQuantity,
        Long treeId,
        Long harvestId
) {}