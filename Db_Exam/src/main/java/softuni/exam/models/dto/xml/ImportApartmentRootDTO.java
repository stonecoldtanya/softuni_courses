package softuni.exam.models.dto.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "apartments")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportApartmentRootDTO {

    @XmlElement(name="apartment")
    private final List<ImportApartmentDTO> apartments;

    public ImportApartmentRootDTO() {
        this.apartments = new ArrayList<>();
    }

    public List<ImportApartmentDTO> getApartments() {
        return apartments;
    }
}
