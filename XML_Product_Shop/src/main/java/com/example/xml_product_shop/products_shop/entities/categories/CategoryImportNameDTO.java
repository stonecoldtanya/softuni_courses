package com.example.xml_product_shop.products_shop.entities.categories;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryImportNameDTO {
    @XmlElement
    private String name;

    public CategoryImportNameDTO() {}

    public String getName() {
        return name;
    }
}
