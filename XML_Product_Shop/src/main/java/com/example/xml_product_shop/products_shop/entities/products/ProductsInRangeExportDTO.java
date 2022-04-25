package com.example.xml_product_shop.products_shop.entities.products;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsInRangeExportDTO {
    @XmlElement(name = "product")
    List<ProductInRangeDTO> products;

    public ProductsInRangeExportDTO() {}

    public ProductsInRangeExportDTO(List<ProductInRangeDTO> products) {
        this.products = products;
    }
}
