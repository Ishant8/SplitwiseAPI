package com.splitwise.advanced.entities.userfriendcircle;

import com.splitwise.advanced.entities.circle.Circle;
import com.splitwise.advanced.entities.userfriend.UserFriend;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "user_friend_circle")
public class UserFriendCircle {

    @EmbeddedId
    UserFriendCircleId ufcId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userFriendId")
    @JoinColumns({
            @JoinColumn(name = "smaller_id", referencedColumnName="smaller_id"),
            @JoinColumn(name = "bigger_id", referencedColumnName="bigger_id")
    })
    private UserFriend userFriend;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("circleId")
    @JoinColumn(name = "circle_id", nullable = false)
    private Circle circle;

    @Column(name = "owes_in_group", precision = 10, scale = 2)
    private BigDecimal owesInGroup;

    public UserFriendCircle() {
    }

    public UserFriendCircle(UserFriendCircleId ufcId, UserFriend userFriend, Circle circle) {
        this.ufcId = ufcId;
        this.userFriend = userFriend;
        this.circle = circle;
    }

    public UserFriendCircleId getUfcId() {
        return ufcId;
    }

    public void setUfcId(UserFriendCircleId ufcId) {
        this.ufcId = ufcId;
    }

    public UserFriend getUserFriend() {
        return userFriend;
    }

    public void setUserFriend(UserFriend userFriend) {
        this.userFriend = userFriend;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public BigDecimal getOwesInGroup() {
        return owesInGroup;
    }

    public void setOwesInGroup(BigDecimal owesInGroup) {
        this.owesInGroup = owesInGroup;
    }

    @Override
    public String toString() {
        return "UserFriendCircle{" +
                "ufcId=" + ufcId +
                ", userFriend=" + userFriend +
                ", circle=" + circle +
                '}';
    }
}
