package com.juls.lab.productmanagementsystem.repository;

import com.juls.lab.productmanagementsystem.data.model.ProductAnalytics;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAnalyticsRepository extends MongoRepository<ProductAnalytics, String> {
    ProductAnalytics findByProductId(String productId);
}
