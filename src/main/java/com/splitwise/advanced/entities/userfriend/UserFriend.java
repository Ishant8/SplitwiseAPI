package com.splitwise.advanced.entities.userfriend;

import com.splitwise.advanced.entities.user.User;
import com.splitwise.advanced.entities.userfriendcircle.UserFriendCircle;
import jakarta.persistence.*;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "user_friend",
       uniqueConstraints = {
        @UniqueConstraint(columnNames = {"smaller_id","bigger_id"})
})
@Check(constraints = "smaller_id < bigger_id")
public class UserFriend {

    @EmbeddedId
    private UserFriendId id;

    @Column(name = "money_owed", precision = 10, scale = 2)
    private BigDecimal moneyOwed;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userIdSmaller")
    @JoinColumn(name = "smaller_id", nullable = false)
    private User smaller;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userIdBigger")
    @JoinColumn(name = "bigger_id", nullable = false)
    private User bigger;

    @OneToMany(mappedBy = "userFriend", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserFriendCircle> userFriendCircle;


    public UserFriend(UserFriendId id, User smaller, User bigger) {
        this.id = id;
        this.smaller = smaller;
        this.bigger = bigger;
    }

    public UserFriend() {
    }

    public UserFriendId getId() {
        return id;
    }

    public void setId(UserFriendId id) {
        this.id = id;
    }

    public User getSmaller() {
        return smaller;
    }

    public void setSmaller(User smaller) {
        this.smaller = smaller;
    }

    public User getBigger() {
        return bigger;
    }

    public void setBigger(User bigger) {
        this.bigger = bigger;
    }

    public BigDecimal getMoneyOwed() {
        return moneyOwed;
    }

    public void setMoneyOwed(BigDecimal moneyOwed) {
        this.moneyOwed = moneyOwed;
    }

    public List<UserFriendCircle> getUserFriendCircle() {
        return userFriendCircle;
    }

    public void setUserFriendCircle(List<UserFriendCircle> userFriendCircle) {
        this.userFriendCircle = userFriendCircle;
    }

    @Override
    public String toString() {
        return "UserFriend{" +
                "id=" + id +
                ", smaller=" + smaller.getFullName() +
                ", bigger=" + bigger.getFullName() +
                '}';
    }
}
