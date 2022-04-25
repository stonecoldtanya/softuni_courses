package com.example.xml_product_shop.products_shop.repositories;

import com.example.xml_product_shop.products_shop.entities.categories.ExportCategoryDTO;
import com.example.xml_product_shop.products_shop.entities.products.Product;
import com.example.xml_product_shop.products_shop.entities.products.ProductInRangeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Integer> {

    List<Product> findAllByPriceBetweenAndBuyerIsNullOrderByPriceAsc(BigDecimal startRange, BigDecimal endRange);


    @Query("""
        SELECT new com.example.xml_product_shop.products_shop.entities.categories.ExportCategoryContentDTO(
        c.name, COUNT(p), AVG(p.price), SUM(p.price))
        FROM Product p
        JOIN p.categories c
        GROUP BY c
        """)
    ExportCategoryDTO getCategoryStats();
}
