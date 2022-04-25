package Task5_BillPaymentsSystem.entities;

import javax.persistence.*;

@Table(name = "Task5_billing_details")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class BillingDetails {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String number;

    public BillingDetails() {

    }

    public BillingDetails(String number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}
