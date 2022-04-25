package com.example.products_shop.services.impl;

import com.example.products_shop.entities.categories.CategoryStats;
import com.example.products_shop.entities.products.ProductWithMissingBuyerDTO;
import com.example.products_shop.repositories.ProductRepository;
import com.example.products_shop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductWithMissingBuyerDTO> getProductsInPriceRangeForSell(float from, float to){
        BigDecimal rangeFrom = BigDecimal.valueOf(from);
        BigDecimal rangeTo = BigDecimal.valueOf(to);
        return this.productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPriceASC(rangeFrom,rangeTo);
    }

    @Override
    public List<CategoryStats> getCategoryStatistics(){
        return this.productRepository.getCategoryStats();
    }
}
