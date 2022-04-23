package softuni.exam.models.dto.xml;
import java.math.BigDecimal;

public class OfferListDTO {
    private long id;
    private String agentFirstName;
    private String agentLastName;
    private double apartmentArea;
    private String apartmentTownName;
    private BigDecimal price;

    public void setId(long id) {
        this.id = id;
    }

    public void setAgentFirstName(String agentFirstName) {
        this.agentFirstName = agentFirstName;
    }

    public void setAgentLastName(String agentLastName) {
        this.agentLastName = agentLastName;
    }

    public void setApartmentArea(double apartmentArea) {
        this.apartmentArea = apartmentArea;
    }

    public void setApartmentTownName(String apartmentTownName) {
        this.apartmentTownName = apartmentTownName;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Agent %s %s with offer №%d", agentFirstName, agentLastName, id)).append(System.lineSeparator());
        builder.append(String.format("-Apartment area: %.2f", apartmentArea)).append(System.lineSeparator());
        builder.append(String.format("--Town: %s", apartmentTownName)).append(System.lineSeparator());
        builder.append(String.format("---Price: %.2f", price)).append(System.lineSeparator());
        builder.append("-------------------------");
        return builder.toString();
    }
}
