package com.juls.lab.productmanagementsystem.service.impl;

import com.juls.lab.productmanagementsystem.data.model.ProductReview;
import com.juls.lab.productmanagementsystem.repository.ProductReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductReviewService {

    private final ProductReviewRepository reviewRepository;

    public void addReview(ProductReview review){
        reviewRepository.save(review);
    }

    public List<ProductReview> getReviewsByProductId(String productId) {
        return reviewRepository.findByProductId(productId);
    }



}
