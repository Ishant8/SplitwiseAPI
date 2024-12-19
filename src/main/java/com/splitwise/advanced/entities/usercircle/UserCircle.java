package com.splitwise.advanced.entities.usercircle;

import com.splitwise.advanced.entities.circle.Circle;
import com.splitwise.advanced.entities.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user_circle",
        uniqueConstraints = {
         @UniqueConstraint(columnNames = {"user_id", "circle_id"})
})
public class UserCircle {

    @EmbeddedId
    private UserCircleId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("circleId")
    @JoinColumn(name = "circle_id",nullable = false)
    private Circle circle;

    public UserCircle() {
    }

    public UserCircle(UserCircleId id, User user, Circle circle) {
        this.id = id;
        this.user = user;
        this.circle = circle;
    }

    public UserCircleId getId() {
        return id;
    }

    public void setId(UserCircleId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    @Override
    public String toString() {
        return "UserCircle{" +
                "user=" + user.getFullName() +
                ", circle=" + circle.getName() +
                ", id=" + id +
                '}';
    }
}
