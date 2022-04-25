package com.example.xml_product_shop.products_shop.entities.users;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserImportDTO {

    @XmlElement(name = "user")
    private List<UserContentImportDTO> users;

    public UserImportDTO() {}

    public List<UserContentImportDTO> getUsers() {
        return users;
    }
}
