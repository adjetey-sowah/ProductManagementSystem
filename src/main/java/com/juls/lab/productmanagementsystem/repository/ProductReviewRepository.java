package com.juls.lab.productmanagementsystem.repository;

import com.juls.lab.productmanagementsystem.data.model.ProductAnalytics;
import com.juls.lab.productmanagementsystem.data.model.ProductReview;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReviewRepository extends MongoRepository<ProductReview, String> {

    List<ProductReview> findByProductId(String productId);

}
