package com.brief.citronix.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "farms")
public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String location;

    private double area;

    @Temporal(TemporalType.DATE)
    private LocalDate creationDate;

//    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL)
//    private List<Field> fields = new ArrayList<>();
}

