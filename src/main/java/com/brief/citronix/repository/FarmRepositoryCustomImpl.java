package com.brief.citronix.repository;

import com.brief.citronix.model.entity.Farm;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FarmRepositoryCustomImpl implements FarmRepositoryCustom {

    private final EntityManager entityManager;

    public FarmRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Farm> searchFarms(String name, String location, Double area) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Farm> query = cb.createQuery(Farm.class);
        Root<Farm> farmRoot = query.from(Farm.class);

        List<Predicate> predicates = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            predicates.add(cb.like(cb.lower(farmRoot.get("name")), "%" + name.toLowerCase() + "%"));
        }

        if (location != null && !location.isEmpty()) {
            predicates.add(cb.like(cb.lower(farmRoot.get("location")), "%" + location.toLowerCase() + "%"));
        }

        if (area != null) {
            predicates.add(cb.equal(farmRoot.get("area"), area));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(query).getResultList();
    }
}
