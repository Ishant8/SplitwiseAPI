package com.splitwise.advanced.dto.request;

import java.math.BigDecimal;

public class PaymentReqDto {
    private int id;
    private BigDecimal amount;
    private int paymentFrom;
    private int paymentTo;
    private String circleName;

    public PaymentReqDto() {
    }

    public PaymentReqDto(int id ,BigDecimal amount, int paymentFrom, int paymentTo, String circleName) {
        this.id = id;
        this.amount = amount;
        this.paymentFrom = paymentFrom;
        this.paymentTo = paymentTo;
        this.circleName = circleName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getPaymentFrom() {
        return paymentFrom;
    }

    public void setPaymentFrom(int paymentFrom) {
        this.paymentFrom = paymentFrom;
    }

    public int getPaymentTo() {
        return paymentTo;
    }

    public void setPaymentTo(int paymentTo) {
        this.paymentTo = paymentTo;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }
}
