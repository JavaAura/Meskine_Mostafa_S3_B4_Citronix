package com.brief.citronix.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "harvestdetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HarvestDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double unitQuantity;

    @ManyToOne
    @JoinColumn(name = "tree_id", nullable = false)
    private Tree tree;

    @ManyToOne
    @JoinColumn(name = "harvest_id", nullable = false)
    private Harvest harvest;
}