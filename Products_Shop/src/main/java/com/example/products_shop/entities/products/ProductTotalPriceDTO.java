package com.example.products_shop.entities.products;

import java.math.BigDecimal;

public class ProductTotalPriceDTO {
    private String name;
    private BigDecimal price;

    public ProductTotalPriceDTO(){

    }
    public ProductTotalPriceDTO(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
