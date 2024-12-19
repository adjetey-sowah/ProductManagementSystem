package com.juls.lab.productmanagementsystem.dto;

import com.juls.lab.productmanagementsystem.data.model.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductMapper {

    public Product toEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantityInStock(productDTO.getQuantityInStock());
        product.setDiscount(BigDecimal.valueOf(productDTO.getDiscount()));
        product.setActive(true);
        return product;
    }

    public ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setProductName(product.getProductName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setQuantityInStock(product.getQuantityInStock());
        dto.setDiscount(product.getDiscount().doubleValue());
        dto.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null);
        return dto;
    }

}
