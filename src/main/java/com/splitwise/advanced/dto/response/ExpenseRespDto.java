package com.splitwise.advanced.dto.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ExpenseRespDto {

    private int id;
    private String name;
    private BigDecimal amount;
    private Date expenseDate;
    private String notes;
    private Map<Integer,String> circle;
    private List<UserExpenseRespDto> involvedUsers;
    private String creator;

    public ExpenseRespDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(Date expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Map<Integer, String> getCircle() {
        return circle;
    }

    public void setCircle(Map<Integer, String> circle) {
        this.circle = circle;
    }

    public List<UserExpenseRespDto> getInvolvedUsers() {
        return involvedUsers;
    }

    public void setInvolvedUsers(List<UserExpenseRespDto> involvedUsers) {
        this.involvedUsers = involvedUsers;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
