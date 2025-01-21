package com.splitwise.advanced.dto.response;

import java.math.BigDecimal;
import java.util.Map;

public class PaymentRespDto {
    private int id;
    private BigDecimal paymentAmount;
    private UserFriendRespDto paymentBetween;
    private Map<Integer, String> circle;

    public PaymentRespDto() {
    }

    public PaymentRespDto(int id, BigDecimal paymentAmount, UserFriendRespDto paymentBetween, Map<Integer, String> circle) {
        this.id = id;
        this.paymentAmount = paymentAmount;
        this.paymentBetween = paymentBetween;
        this.circle = circle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public UserFriendRespDto getPaymentBetween() {
        return paymentBetween;
    }

    public void setPaymentBetween(UserFriendRespDto paymentBetween) {
        this.paymentBetween = paymentBetween;
    }

    public Map<Integer, String> getCircle() {
        return circle;
    }

    public void setCircle(Map<Integer, String> circle) {
        this.circle = circle;
    }
}
