package com.example.xml_product_shop.products_shop.services.impl;

import com.example.xml_product_shop.products_shop.entities.categories.Category;
import com.example.xml_product_shop.products_shop.entities.categories.ExportCategoryContentDTO;
import com.example.xml_product_shop.products_shop.entities.categories.ExportCategoryDTO;
import com.example.xml_product_shop.products_shop.entities.products.Product;
import com.example.xml_product_shop.products_shop.entities.products.ProductInRangeDTO;
import com.example.xml_product_shop.products_shop.entities.products.ProductsInRangeExportDTO;
import com.example.xml_product_shop.products_shop.entities.users.User;
import com.example.xml_product_shop.products_shop.entities.users.UserWithSoldProductsDTO;
import com.example.xml_product_shop.products_shop.repositories.ProductRepository;
import com.example.xml_product_shop.products_shop.services.ProductService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper mapper;
    private final TypeMap<Product, ProductInRangeDTO> productToDtoMapping;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.mapper = new ModelMapper();

        Converter<User, String> userToFullNameConverter =
                context -> context.getSource() == null ? null : context.getSource().getFullName();

        TypeMap<Product, ProductInRangeDTO> typeMap =
                this.mapper.createTypeMap(Product.class, ProductInRangeDTO.class);

        this.productToDtoMapping = typeMap.addMappings(map ->
                map.using(userToFullNameConverter)
                        .map(Product::getSeller, ProductInRangeDTO::setSeller));
    }

    @Override
    public ProductsInRangeExportDTO getInRange(float from, float to) {
        BigDecimal startRange = BigDecimal.valueOf(from);
        BigDecimal endRange = BigDecimal.valueOf(to);

        List<Product> products = this.productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPriceAsc(startRange, endRange);

        List<ProductInRangeDTO> exportDtoProduct = products
                .stream()
                .map(this.productToDtoMapping::map)
                .collect(Collectors.toList());

        return new ProductsInRangeExportDTO(exportDtoProduct);

    }

    @Override
    public ExportCategoryDTO getCategoryStatistics(){
        List<Category> categories = (List<Category>) this.productRepository.getCategoryStats();

        List<ExportCategoryContentDTO> dtos =
                categories
                        .stream()
                        .map(u -> this.mapper.map(u, ExportCategoryContentDTO.class))
                        .collect(Collectors.toList());

        return new ExportCategoryDTO();
    }
}
