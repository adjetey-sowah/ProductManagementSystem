package com.juls.lab.productmanagementsystem.data.model;

import jakarta.persistence.Access;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "product_analytics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAnalytics {

    @Id
    private String id;
    private String productId;
    private int views;
    private int purchases;
    private LocalDateTime lastUpdated;
}
