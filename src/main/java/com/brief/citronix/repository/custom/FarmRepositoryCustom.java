package com.brief.citronix.repository.custom;

import com.brief.citronix.model.entity.Farm;

import java.util.List;

public interface FarmRepositoryCustom {
    List<Farm> searchFarms(String name, String location, Double area);
}