package exam.model.dto.xml;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportShopDTO {
    @Size(min = 4)
    private String address;

    @Min(1)
    @Max(50)
    @XmlElement(name = "employee-count")
    private int employeeCount;

    @Min(20000)
    private int income;

    @Size(min = 4)
    private String name;

    @Min(50)
    @XmlElement(name = "shop-area")
    private int shopArea;

    @XmlElement(name = "town")
    private ShopTownDTO town;

    public ImportShopDTO() {
    }

    public String getAddress() {
        return address;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public int getIncome() {
        return income;
    }

    public String getName() {
        return name;
    }

    public int getShopArea() {
        return shopArea;
    }

    public ShopTownDTO getTown() {
        return town;
    }
}
