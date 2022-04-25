package Task5_BillPaymentsSystem.entities;

import javax.persistence.*;

@Table(name = "Task5_bank_accounts")
public class BankAccount extends BillingDetails {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "code", nullable = false)
    private String swiftCode;

    public BankAccount() {

    }

    public BankAccount(String number, String bankName, String swiftCode) {
        super(number);
        this.bankName = bankName;
        this.swiftCode = swiftCode;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }
}
