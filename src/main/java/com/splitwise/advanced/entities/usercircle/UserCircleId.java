package com.splitwise.advanced.entities.usercircle;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserCircleId implements Serializable {

    private int userId;
    private int circleId;

    public UserCircleId() {
    }

    public UserCircleId(int userId, int circleId) {
        this.userId = userId;
        this.circleId = circleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCircleId() {
        return circleId;
    }

    public void setCircleId(int circleId) {
        this.circleId = circleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserCircleId)) return false;

        UserCircleId that = (UserCircleId) o;

        return userId == that.userId && circleId == that.circleId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, circleId);
    }

}
