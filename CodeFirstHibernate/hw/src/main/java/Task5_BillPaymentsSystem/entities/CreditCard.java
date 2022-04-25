package Task5_BillPaymentsSystem.entities;

import javax.persistence.*;
import java.time.Month;
import java.time.Year;

@Table(name = "Task5_credit_cards")
public class CreditCard extends BillingDetails {
    @Id
    @GeneratedValue
    private int id;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Column(name = "expiration_month", nullable = false)
    private Month expirationMonth;

    @Column(name = "expiration_year", nullable = false)
    private Year expirationYear;


    public CreditCard() {

    }

    public CreditCard(String number, CardType cardType, Month expirationMonth, Year expirationYear) {
        super(number);
        this.cardType = cardType;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public Month getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(Month expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public Year getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(Year expirationYear) {
        this.expirationYear = expirationYear;
    }

}
