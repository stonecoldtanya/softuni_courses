package exam.model.dto.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "shops")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportShopRootDTO {
    @XmlElement(name = "shop")
    private List<ImportShopDTO> shops;

    public ImportShopRootDTO() {
    }

    public List<ImportShopDTO> getShops() {
        return shops;
    }
}
