package com.splitwise.advanced.entities.payment;

import com.splitwise.advanced.entities.circle.Circle;
import com.splitwise.advanced.entities.userfriend.UserFriend;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "payment_amount",precision = 10, scale = 2)
    private BigDecimal paymentAmount;

    @ManyToOne()
    @JoinColumns({
            @JoinColumn(name = "smaller_id", referencedColumnName = "smaller_id"),
            @JoinColumn(name = "bigger_id", referencedColumnName = "bigger_id"),
    })
    private UserFriend userFriend;

    @ManyToOne()
    @JoinColumn(name = "circle_id")
    private Circle circle;

    public Payment() {
    }

    public Payment(BigDecimal paymentAmount, UserFriend userFriend, Circle circle) {
        this.paymentAmount = paymentAmount;
        this.userFriend = userFriend;
        this.circle = circle;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", paymentAmount=" + paymentAmount +
                ", userFriend=" + userFriend +
                ", circle=" + circle +
                '}';
    }
}
