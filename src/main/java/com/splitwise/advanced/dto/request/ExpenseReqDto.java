package com.splitwise.advanced.dto.request;

import java.math.BigDecimal;
import java.util.List;

public class ExpenseReqDto {

    private String name;
    private BigDecimal amount;
    private String notes;
    private String circleName;
    private List<String> userShares;
    private String creatorName;

    public ExpenseReqDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public List<String> getUserShares() {
        return userShares;
    }

    public void setUserShares(List<String> userShares) {
        this.userShares = userShares;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    @Override
    public String toString() {
        return "ExpenseReqDto{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                ", notes='" + notes + '\'' +
                ", circleName='" + circleName + '\'' +
                ", userShares= " + userShares +
                ", creatorName='" + creatorName + '\'' +
                '}';
    }
}
