package com.brief.citronix.repository;

import com.brief.citronix.model.entity.Farm;
import com.brief.citronix.repository.custom.FarmRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long>, FarmRepositoryCustom {
}
