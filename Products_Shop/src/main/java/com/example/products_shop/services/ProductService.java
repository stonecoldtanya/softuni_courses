package com.example.products_shop.services;

import com.example.products_shop.entities.categories.CategoryStats;
import com.example.products_shop.entities.products.ProductWithMissingBuyerDTO;

import java.util.List;

public interface ProductService {
    List<ProductWithMissingBuyerDTO> getProductsInPriceRangeForSell(float from, float to);

    List<CategoryStats> getCategoryStatistics();
}
