package com.example.xml_product_shop.products_shop.entities.users;

import com.example.xml_product_shop.products_shop.entities.products.ExportSoldProductDTO;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserWithSoldProductsDTO {

    @XmlAttribute(name = "first-name")
    private String firstName;

    @XmlAttribute(name = "last-name")
    private String lastName;

    @XmlElementWrapper(name = "sold-products")
    @XmlElement(name = "product")
    List<ExportSoldProductDTO> soldItems ;

    public UserWithSoldProductsDTO() {

    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<ExportSoldProductDTO> getSoldItems() {
        return soldItems ;
    }

    public void setSoldItems(List<ExportSoldProductDTO> soldItems ) {
        this.soldItems  = soldItems ;
    }
}
