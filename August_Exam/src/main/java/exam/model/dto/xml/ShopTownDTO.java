package exam.model.dto.xml;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class ShopTownDTO {
    @Size(min = 2)
    private String name;

    public String getName() {
        return name;
    }
}
