package com.juls.lab.productmanagementsystem.controller;

import com.juls.lab.productmanagementsystem.data.model.ProductReview;
import com.juls.lab.productmanagementsystem.service.impl.ProductReviewService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ProductReviewController {

    private final ProductReviewService reviewService;

    @PostMapping
    public ResponseEntity<ProductReview> addReview(@RequestBody ProductReview productReview){
        ProductReview newReview = this.reviewService.addReview(productReview);
        return ResponseEntity.ok(newReview);
    }

    @GetMapping("/{productId}")
    @Schema(name = "Product Review Controller")
    public ResponseEntity<List<ProductReview>> getReviews(@PathVariable String productId){
        try{
        List<ProductReview> productReviews = this.reviewService.getReviewsByProductId(productId);
        return ResponseEntity.ok().body(productReviews);
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
