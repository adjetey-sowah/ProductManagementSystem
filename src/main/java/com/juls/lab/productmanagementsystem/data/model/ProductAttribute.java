package com.juls.lab.productmanagementsystem.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Entity
@Data
@RequiredArgsConstructor
public class ProductAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attributeId;

    @ManyToOne
    @JsonBackReference
    private Product product;

    private String name;
    private String value;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductAttribute that = (ProductAttribute) o;
        return Objects.equals(attributeId, that.getAttributeId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributeId);
    }

}
