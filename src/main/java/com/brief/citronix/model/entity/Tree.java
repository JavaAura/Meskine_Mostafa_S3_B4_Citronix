package com.brief.citronix.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trees")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate plantingDate;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    @OneToMany(mappedBy = "tree", cascade = CascadeType.ALL)
    private List<HarvestDetail> harvestDetails = new ArrayList<>();

    // Calculate age based on the planting date
    @Transient
    public int getAge() {
        return plantingDate != null ? (int) ChronoUnit.YEARS.between(plantingDate, LocalDate.now()) : 0;
    }

    // Calculate productivity based on the age
    @Transient
    public int getProductivity() {
        int age = getAge();
        if (age < 3) {
            return 2;
        } else if (age <= 10) {
            return 12;
        } else {
            return 20;
        }
    }

    @PrePersist
    @PreUpdate
    private void validatePlantingDate() {
        if (plantingDate != null) {
            Month plantingMonth = plantingDate.getMonth();
            if (plantingMonth != Month.MARCH && plantingMonth != Month.APRIL && plantingMonth != Month.MAY) {
                throw new IllegalArgumentException("Planting date must be within the period of March to May.");
            }
        }
    }
}