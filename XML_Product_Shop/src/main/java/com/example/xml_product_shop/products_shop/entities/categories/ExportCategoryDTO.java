package com.example.xml_product_shop.products_shop.entities.categories;

import com.example.xml_product_shop.products_shop.entities.users.UserWithSoldProductsDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExportCategoryDTO {

    @XmlElement(name = "category")
    List<ExportCategoryContentDTO> categories;

    public ExportCategoryDTO() {

    }

    public ExportCategoryDTO(List<ExportCategoryContentDTO> categories) {
        this.categories = categories;
    }
}
