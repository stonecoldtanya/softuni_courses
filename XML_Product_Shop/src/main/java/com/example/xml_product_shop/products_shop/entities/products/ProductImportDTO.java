package com.example.xml_product_shop.products_shop.entities.products;

import javax.xml.bind.annotation.*;
import java.util.List;
@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductImportDTO {

    @XmlElement(name = "product")
    private List<ProductContentDTO> products;

    public ProductImportDTO() {

    }

    public List<ProductContentDTO> getProducts() {
        return products;
    }
}
