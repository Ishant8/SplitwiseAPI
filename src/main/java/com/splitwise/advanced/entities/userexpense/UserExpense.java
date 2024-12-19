package com.splitwise.advanced.entities.userexpense;

import com.splitwise.advanced.entities.expense.Expense;
import com.splitwise.advanced.entities.user.User;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "user_expense")
public class UserExpense {

    @EmbeddedId
    private UserExpenseId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("expenseId")
    @JoinColumn(name = "expense_id", nullable = false)
    private Expense expense;

    @Column(name = "expense_share", precision = 10, scale = 2)
    private BigDecimal expenseShare;

    public UserExpense() {
    }

    public UserExpense(UserExpenseId id, User user, Expense expense, BigDecimal expenseShare) {
        this.id = id;
        this.user = user;
        this.expense = expense;
        this.expenseShare = expenseShare;
    }

    public UserExpenseId getId() {
        return id;
    }

    public void setId(UserExpenseId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public BigDecimal getExpenseShare() {
        return expenseShare;
    }

    public void setExpenseShare(BigDecimal expenseShare) {
        this.expenseShare = expenseShare;
    }

    @Override
    public String toString() {
        return "UserExpense{" +
                "id=" + id +
                ", user=" + user +
                ", expense=" + expense +
                ", expenseShare=" + expenseShare +
                '}';
    }
}
