package com.example.xml_product_shop.products_shop.entities.users;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class SellersExportDTO {

    @XmlElement(name = "user")
    List<UserWithSoldProductsDTO> users;

    public SellersExportDTO() {

    }

    public SellersExportDTO(List<UserWithSoldProductsDTO> users) {
        this.users = users;
    }

}
