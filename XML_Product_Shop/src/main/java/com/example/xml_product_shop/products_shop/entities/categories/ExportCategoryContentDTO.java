package com.example.xml_product_shop.products_shop.entities.categories;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExportCategoryContentDTO {
    @XmlAttribute
    private String name;

    @XmlElement(name = "products-count")
    private long productCount;

    @XmlElement(name = "average-price")
    private double averagePrice;

    @XmlElement(name = "total-revenue")
    private BigDecimal totalRevenue;

    public ExportCategoryContentDTO() {
    }

    public ExportCategoryContentDTO(String name, long productCount, double averagePrice, BigDecimal totalRevenue) {
        this.name = name;
        this.productCount = productCount;
        this.averagePrice = averagePrice;
        this.totalRevenue = totalRevenue;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProductCount(long productCount) {
        this.productCount = productCount;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
