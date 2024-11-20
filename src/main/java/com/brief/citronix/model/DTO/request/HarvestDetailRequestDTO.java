package com.brief.citronix.model.DTO.request;

public record HarvestDetailRequestDTO(
        double unitQuantity,
        Long treeId,
        Long harvestId
) {}