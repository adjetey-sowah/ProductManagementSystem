package com.juls.lab.productmanagementsystem.model;

import jakarta.persistence.*;
import lombok.*;

import com.juls.lab.productmanagementsystem.model.Category;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;
    private String description;
    private double price;
    private int quantityInStock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductAttribute> productAttribute;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;






}

