package softuni.exam.models.dto.xml;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "apartment")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportApartmentDTO {

    @XmlElement
    @Pattern(regexp = "^(two_rooms|three_rooms|four_rooms)+$")
    private String apartmentType;

    @Min(40)
    @XmlElement
    private double area;

    @XmlElement
    private String town;

    public ImportApartmentDTO() {
    }

    public String getApartmentType() {
        return apartmentType;
    }

    public double getArea() {
        return area;
    }

    public String getTown() {
        return town;
    }
}
