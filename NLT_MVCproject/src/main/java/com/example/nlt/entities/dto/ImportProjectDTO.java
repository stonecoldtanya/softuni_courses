package com.example.nlt.entities.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportProjectDTO implements Serializable {
    @XmlElement
    private String name;

    @XmlElement
    @Size(min = 14)
    private String description;

    @XmlElement(name = "start-date")
    private String startDate;

    @XmlElement(name = "is-finished")
    private boolean isFinished;

    @XmlElement
    @Positive
    private BigDecimal payment;

    @XmlElement
    private ImportCompanyDTO company;

    public ImportProjectDTO() {}

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStartDate() {
        return startDate;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public ImportCompanyDTO getCompany() {
        return company;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public void setCompany(ImportCompanyDTO company) {
        this.company = company;
    }
}