package com.juls.lab.productmanagementsystem.repository;

import com.juls.lab.productmanagementsystem.model.Category;
import com.juls.lab.productmanagementsystem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductByCategory(Category category);
    List<Product> findByProductNameContainingIgnoreCaseAAndActiveTrue(String keyword);
    List<Product> findByPriceBetweenAndActiveTrue(double price, double price2);
    List<Product> findByDiscountGreaterThanAndActiveTrue(BigDecimal discount);
    @Query("SELECT p FROM Product p JOIN p.productAttribute attr where attr.name = :attrName AND attr.value = :attrValue")
    List<Product> findByProductAttributeNameAndValue(String attrName, String attrValue);


}
