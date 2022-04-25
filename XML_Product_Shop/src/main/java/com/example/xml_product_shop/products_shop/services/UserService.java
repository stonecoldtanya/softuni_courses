package com.example.xml_product_shop.products_shop.services;



import com.example.xml_product_shop.products_shop.entities.users.ExportSellersWithCountsDTO;
import com.example.xml_product_shop.products_shop.entities.users.SellersExportDTO;

import java.util.List;

public interface UserService {

    SellersExportDTO findAllWithSoldProducts();

//    ExportSellersWithCountsDTO findAllWithSoldProductsAndCounts();
}
