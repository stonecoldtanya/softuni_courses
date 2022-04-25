package com.example.xml_product_shop.products_shop.services;


import com.example.xml_product_shop.products_shop.entities.categories.ExportCategoryDTO;
import com.example.xml_product_shop.products_shop.entities.products.ProductsInRangeExportDTO;

public interface ProductService {

    ProductsInRangeExportDTO getInRange(float from, float to);

    ExportCategoryDTO getCategoryStatistics();
}
