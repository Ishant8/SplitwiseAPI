package com.splitwise.advanced.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.splitwise.advanced.entities.expense.Expense;
import com.splitwise.advanced.entities.usercircle.UserCircle;
import com.splitwise.advanced.entities.usercircle.UserCircleId;
import com.splitwise.advanced.entities.userexpense.UserExpense;
import com.splitwise.advanced.entities.userfriend.UserFriend;
import com.splitwise.advanced.entities.userfriend.UserFriendId;
import com.splitwise.advanced.entities.circle.Circle;
import com.splitwise.advanced.entities.currency.Currency;
import com.splitwise.advanced.entities.preference.Preference;
import com.splitwise.advanced.entities.timezone.TimeZone;
import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "full_name", length = 100)
    private String fullName;

    @NotNull
    @Column(name = "email", unique = true, length = 100, nullable = false)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @NotNull                         //this validation can be triggered anywhere using the @Valid annotation
    @Column(name = "password", length = 60, nullable = false)
    private String password;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "timezone_id")
    private TimeZone timeZone;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @OneToOne(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private Preference preference = new Preference(this);

    @JsonIgnore
    @OneToMany(mappedBy = "smaller", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserFriend> friendsLinkedAsSmaller = new ArrayList<>();


    @JsonIgnore
    @OneToMany(mappedBy = "bigger", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserFriend> friendsLinkedAsBigger = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCircle> userCircleList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserExpense> userExpenseList;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expense> expenseList;

    public User() {
    }

    public User(String fullName, String email, String phone, String password) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Preference getPreference() {
        return preference;
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
    }

    public List<UserFriend> getFriendsLinkedAsSmaller() {
        return friendsLinkedAsSmaller;
    }

    public void setFriendsLinkedAsSmaller(List<UserFriend> friendsLinkedAsSmaller) {
        this.friendsLinkedAsSmaller = friendsLinkedAsSmaller;
    }

    public List<UserFriend> getFriendsLinkedAsBigger() {
        return friendsLinkedAsBigger;
    }

    public void setFriendsLinkedAsBigger(List<UserFriend> friendsLinkedAsBigger) {
        this.friendsLinkedAsBigger = friendsLinkedAsBigger;
    }

    public UserCircle getUserCircleList() {
        return userCircleList.get(0);
    }

    public void setUserCircleList(List<UserCircle> userCircleList) {
        this.userCircleList = userCircleList;
    }

    public List<String> getFriendsAsString() {
        List<String> allFriends = new ArrayList<>();

        for(UserFriend friend : friendsLinkedAsSmaller) {
            allFriends.add(friend.getBigger().getFullName()+ (friend.getMoneyOwed().doubleValue()<0?" Owes ":" Lent ") + friend.getMoneyOwed().abs());
        }

        for (UserFriend friend : friendsLinkedAsBigger){
            allFriends.add(friend.getSmaller().getFullName() + (friend.getMoneyOwed().doubleValue()<0?" Lent ":" Owes ") + friend.getMoneyOwed().abs());
        }

        return allFriends;
    }

    @JsonIgnore
    public List<User> getFriends() {
        List<User> allFriends = new ArrayList<>();

        for(UserFriend friend : friendsLinkedAsSmaller) {
            allFriends.add(friend.getBigger());
        }

        for (UserFriend friend : friendsLinkedAsBigger){
            allFriends.add(friend.getSmaller());
        }

        return allFriends;
    }



    public void addFriend(User friend) {

        if(this.equals(friend)) {
            return;
        }

        User smaller = this.getId() < friend.getId() ? this : friend;
        User bigger = this.equals(smaller) ? friend : this;

        UserFriendId id = new UserFriendId(smaller.getId(),bigger.getId());
        UserFriend uf = new UserFriend(id, BigDecimal.valueOf(0.00), smaller,bigger);

        if (smaller == this) {
            this.friendsLinkedAsSmaller.add(uf);
        } else {
            this.friendsLinkedAsBigger.add(uf);
        }

//        if (bigger == friend) {
//            friend.friendsLinkedAsBigger.add(uf);
//        } else {
//            friend.friendsLinkedAsSmaller.add(uf);
//        }
    }

    public void addCircle(Circle circle) {

        UserCircle userCircle = new UserCircle(new UserCircleId(this.id,circle.getId()), this, circle);

        this.userCircleList.add(userCircle);
    }

    public List<String> getCircles() {
        List<String> circles = this.userCircleList!=null?this.userCircleList.stream().map(userCircle -> userCircle.getCircle().getName()).toList():null;
        return circles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", timeZone=" + timeZone +
                ", currency=" + currency +
                ", preference=" + preference +
                ", friendsLinkedAsSmaller=" + friendsLinkedAsSmaller +
                ", friendsLinkedAsBigger=" + friendsLinkedAsBigger +
                ", userCircle=" + userCircleList +
                '}';
    }
}


