package com.juls.lab.productmanagementsystem.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ProductAttribute {

    @Id
    @Column(name = "attribute_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    private String name;
    private String value;
}
