package com.splitwise.advanced.entities.circle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.splitwise.advanced.entities.usercircle.UserCircle;
import com.splitwise.advanced.entities.userfriend.UserFriend;
import com.splitwise.advanced.entities.userfriendcircle.UserFriendCircle;
import com.splitwise.advanced.entities.userfriendcircle.UserFriendCircleId;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "circle")
public class Circle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private int id;

    @Column(name = "name", length = 100)
    private String name;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "circle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCircle> users = new ArrayList<>();

    @OneToMany(mappedBy = "circle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserFriendCircle> userFriendCircle = new ArrayList<>();

    public Circle() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<UserCircle> getUsers() {
        return users;
    }

    public void setUsers(List<UserCircle> users) {
        this.users = users;
    }

    public List<String> getUserFriendCircle() {
        return userFriendCircle.stream().map(ufc -> ufc.getCircle().getName()+" "+ufc.getUserFriend().getBigger().getFullName()+" "+ufc.getUserFriend().getSmaller().getFullName()+" Balance "+ufc.getOwesInGroup()).toList();
    }

    public void setUserFriendCircle(List<UserFriendCircle> userFriendCircle) {
        this.userFriendCircle = userFriendCircle;
    }

    public List<String> getUsersList(){
        List<String> users = this.users.stream().map(userCircle -> userCircle.getUser().getFullName()).toList();
        return users;
    }

    public void addUserFriend(UserFriend userFriend){
        UserFriendCircleId userFriendCircleId = new UserFriendCircleId(userFriend.getId(),this.getId());
        UserFriendCircle userFriendCircle = new UserFriendCircle(userFriendCircleId,userFriend,this);
        this.userFriendCircle.add(userFriendCircle);
    }

    public void removeUser(String userName){
        UserCircle userCircle = users.stream().filter(userCirc -> userCirc.getUser().getFullName().equals(userName)).findFirst().orElse(null);
        users.remove(userCircle);
        UserFriendCircle ufc1 = userFriendCircle.stream().filter(ufc -> ufc.getUserFriend().getSmaller().getFullName().equals(userName) || ufc.getUserFriend().getBigger().getFullName().equals(userName)).findFirst().orElse(null);
        if(ufc1 != null) {
            userFriendCircle.remove(ufc1);
        }
    }

    @Override
    public String toString() {
        return "Circle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", users=" + users +
                '}';
    }
}
