package com.splitwise.advanced.entities.userfriendcircle;

import com.splitwise.advanced.entities.userfriend.UserFriendId;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserFriendCircleId implements Serializable {

    private UserFriendId userFriendId;
    private int circleId;

    public UserFriendCircleId() {

    }
    public UserFriendCircleId(UserFriendId userFriendId, int circleId) {
        this.userFriendId = userFriendId;
        this.circleId = circleId;
    }

    public UserFriendId getUserFriendId() {
        return userFriendId;
    }

    public void setUserFriendId(UserFriendId userFriendId) {
        this.userFriendId = userFriendId;
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
        if (!(o instanceof UserFriendCircleId)) return false;
        UserFriendCircleId that = (UserFriendCircleId) o;

        return Objects.equals(userFriendId,that.userFriendId) && Objects.equals(circleId,that.circleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userFriendId, circleId);
    }
}
