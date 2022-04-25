package com.example.xml_product_shop.products_shop.entities.products;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductContentDTO {

    @XmlElement
    private String name;

    @XmlElement
    private BigDecimal price;

    public ProductContentDTO() {

    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
