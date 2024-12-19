package com.juls.lab.productmanagementsystem.service.impl;

import com.juls.lab.productmanagementsystem.data.model.ProductAnalytics;
import com.juls.lab.productmanagementsystem.repository.ProductAnalyticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductAnalyticsService {

    private final ProductAnalyticsRepository analyticsRepository;

    public void updateAnalytics(String productId, boolean purchased){
        ProductAnalytics analytics = analyticsRepository.findByProductId(productId);
        if (analytics == null) {
            analytics = new ProductAnalytics();
            analytics.setProductId(productId);
            analytics.setViews(1);
            analytics.setPurchases(purchased ? 1 : 0);
        } else {
            analytics.setViews(analytics.getViews() + 1);
            if (purchased) {
                analytics.setPurchases(analytics.getPurchases() + 1);
            }
        }
        analytics.setLastUpdated(LocalDateTime.now());
       analyticsRepository.save(analytics);
    }


}
