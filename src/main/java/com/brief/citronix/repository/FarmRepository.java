package com.brief.citronix.repository;

import com.brief.citronix.model.entity.Farm;
import com.brief.citronix.repository.custom.FarmRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmRepository extends JpaRepository<Farm, Long>, FarmRepositoryCustom {
}
