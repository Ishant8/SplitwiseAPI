package com.splitwise.advanced.entities.userexpense;

import com.splitwise.advanced.entities.usercircle.UserCircleId;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class UserExpenseId {

    private int userId;
    private int expenseId;

    public UserExpenseId() {}

    public UserExpenseId(int userId, int expenseId) {
        this.userId = userId;
        this.expenseId = expenseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, expenseId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserExpenseId)) return false;

        UserExpenseId that = (UserExpenseId) o;

        return userId == that.userId && expenseId == that.expenseId;
    }
}
