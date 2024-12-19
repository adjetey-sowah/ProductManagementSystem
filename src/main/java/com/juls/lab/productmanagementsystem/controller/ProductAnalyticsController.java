package com.juls.lab.productmanagementsystem.controller;

import com.juls.lab.productmanagementsystem.service.impl.ProductAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class ProductAnalyticsController {

    private final ProductAnalyticsService analyticsService;

    @PostMapping("/{productId}/purchase")
    public void trackPurchase(@PathVariable String productId) {
        analyticsService.updateAnalytics(productId, true);
    }

    @PostMapping("/{productId}/view")
    public void trackView(@PathVariable String productId) {
        analyticsService.updateAnalytics(productId, false);
    }
}
