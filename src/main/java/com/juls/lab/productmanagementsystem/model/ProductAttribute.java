package com.juls.lab.productmanagementsystem.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ProductAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attributeId;

    @ManyToOne
    private Product product;

    private String name;
    private String value;
}
