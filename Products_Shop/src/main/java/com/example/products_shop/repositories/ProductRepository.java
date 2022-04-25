package com.example.products_shop.repositories;

import com.example.products_shop.entities.categories.CategoryStats;
import com.example.products_shop.entities.products.Product;
import com.example.products_shop.entities.products.ProductWithMissingBuyerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Integer> {
    @Query("""
        SELECT new com.example.products_shop.entities.products.ProductWithMissingBuyerDTO(
        p.name, p.price, p.seller.firstName, p.seller.lastName)
        FROM Product p
        WHERE p.price > :rangeFrom AND p.price < :rangeTo AND p.buyer IS NULL
        ORDER BY p.price ASC 
         """)
    List<ProductWithMissingBuyerDTO> findAllByPriceBetweenAndBuyerIsNullOrderByPriceASC(BigDecimal rangeFrom, BigDecimal rangeTo);

    @Query("""
        SELECT new com.example.products_shop.entities.categories.CategoryStats(
        c.name, COUNT(p), AVG(p.price), SUM(p.price))
        FROM Product p
        JOIN p.categories c
        GROUP BY c
        """)
    List<CategoryStats> getCategoryStats();
}
