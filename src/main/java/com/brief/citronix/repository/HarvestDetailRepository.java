package com.brief.citronix.repository;

import com.brief.citronix.model.entity.HarvestDetail;
import com.brief.citronix.model.enums.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HarvestDetailRepository extends JpaRepository<HarvestDetail, Long> {
    @Query("SELECT COUNT(hd) > 0 FROM HarvestDetail hd " +
            "WHERE hd.tree.id = :treeId " +
            "AND hd.harvest.season = :season " +
            "AND EXTRACT(YEAR FROM hd.harvest.date) = :year")
    boolean isTreeHarvestedInSeason(Long treeId, Season season, int year);

    @Query("SELECT SUM(hd.unitQuantity) FROM HarvestDetail hd WHERE hd.harvest.id = :harvestId")
    double sumUnitQuantityByHarvest(@Param("harvestId") Long harvestId);
}

