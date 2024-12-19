package com.splitwise.advanced.entities.userfriend;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserFriendId implements Serializable {

    private int userIdSmaller;
    private int userIdBigger;

    public UserFriendId(){}

    public UserFriendId(int userIdSmaller, int userIdBigger) {
        this.userIdBigger = userIdBigger;
        this.userIdSmaller = userIdSmaller;
    }

    public int getUserIdSmaller() {
        return userIdSmaller;
    }

    public void setUserIdSmaller(int userIdSmaller) {
        this.userIdSmaller = userIdSmaller;
    }

    public int getUserIdBigger() {
        return userIdBigger;
    }

    public void setUserIdBigger(int userIdBigger) {
        this.userIdBigger = userIdBigger;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserFriendId)) return false;
        UserFriendId that = (UserFriendId) o;

        return Objects.equals(userIdSmaller,that.userIdSmaller) && Objects.equals(userIdBigger,that.userIdBigger);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userIdSmaller, userIdBigger);
    }
}
