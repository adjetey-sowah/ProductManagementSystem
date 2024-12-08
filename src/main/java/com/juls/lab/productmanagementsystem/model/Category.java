package com.juls.lab.productmanagementsystem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String categoryName;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private Set<Category> subCategories;

    @OneToMany(mappedBy = "category")
    private Set<Product> products;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
