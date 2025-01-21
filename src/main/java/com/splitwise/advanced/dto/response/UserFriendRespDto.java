package com.splitwise.advanced.dto.response;

import java.math.BigDecimal;
import java.util.Map;

public class UserFriendRespDto {
    private BigDecimal moneyOwed;
    private Map<Integer, String> smaller;
    private Map<Integer, String> bigger;

    public UserFriendRespDto() {
    }

    public UserFriendRespDto(BigDecimal moneyOwed, Map<Integer, String> smaller, Map<Integer, String> larger) {
        this.moneyOwed = moneyOwed;
        this.smaller = smaller;
        this.bigger = larger;
    }

    public BigDecimal getMoneyOwed() {
        return moneyOwed;
    }

    public void setMoneyOwed(BigDecimal moneyOwed) {
        this.moneyOwed = moneyOwed;
    }

    public Map<Integer, String> getSmaller() {
        return smaller;
    }

    public void setSmaller(Map<Integer, String> smaller) {
        this.smaller = smaller;
    }

    public Map<Integer, String> getBigger() {
        return bigger;
    }

    public void setBigger(Map<Integer, String> bigger) {
        this.bigger = bigger;
    }
}
