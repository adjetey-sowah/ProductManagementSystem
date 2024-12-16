package com.juls.lab.productmanagementsystem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductDTO {

    @NotNull
    @Size(min = 1, max = 100)
    private String productName;
    @Size(max = 500)
    private String description;
    private double price;
    private int quantityInStock;
    private Long categoryId;
    private double discount;
}
