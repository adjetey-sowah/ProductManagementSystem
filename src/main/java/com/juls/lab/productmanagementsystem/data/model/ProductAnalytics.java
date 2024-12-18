package com.juls.lab.productmanagementsystem.data.model;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "product_analytics")
@Data
public class ProductAnalytics {

    @Id
    private String id;
    private String productId;
    private int views;
    private int purchases;
    private LocalDateTime lastUpdated;
}
