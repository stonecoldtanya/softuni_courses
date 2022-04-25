package com.example.xml_product_shop.products_shop.entities.categories;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "categories")
public class CategoryImportDTO {
    @XmlElement(name = "category")
    private List<CategoryImportNameDTO> categories;

    public CategoryImportDTO() {}

    public List<CategoryImportNameDTO> getCategories() {
        return categories;
    }


}
