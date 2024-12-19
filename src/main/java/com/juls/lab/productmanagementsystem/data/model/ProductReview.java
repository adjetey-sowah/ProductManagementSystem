package com.juls.lab.productmanagementsystem.data.model;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "product_reviews")
@Data
public class ProductReview {

    @Id
    private String id;
    private String productId;
    private String userId;
    private String review;
    private int rating;
    private LocalDateTime createdAt;
}
