package com.brief.citronix.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fields")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double area;

    @ManyToOne
    @JoinColumn(name = "farm_id", nullable = false)
    private Farm farm;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<Tree> trees = new ArrayList<>();

    @PrePersist
    @PreUpdate
    private void validateFieldArea() {
        if (farm == null) {
            throw new IllegalArgumentException("Farm must be associated with a field.");
        }

        // Validate field area against half the farm's area
        if (this.area > (farm.getArea() / 2)) {
            throw new IllegalArgumentException("Field area exceeds half the farm's area.");
        }

        // Check the number of fields associated with the farm
        long numberOfFields = farm.getFields().stream()
                .filter(field -> !field.equals(this))
                .count();

        if (numberOfFields >= 10) {
            throw new IllegalArgumentException("A farm cannot have more than 10 fields.");
        }

        // Calculate the total area of existing fields (excluding this field if updating)
        double totalFieldArea = farm.getFields().stream()
                .filter(field -> !field.equals(this))
                .mapToDouble(Field::getArea)
                .sum();

        // Check if the new total field area exceeds the farm's area
        double newTotalArea = totalFieldArea + this.area;
        if (newTotalArea > farm.getArea()) {
            throw new IllegalArgumentException("Total field area exceeds the farm's total area.");
        }
    }
}