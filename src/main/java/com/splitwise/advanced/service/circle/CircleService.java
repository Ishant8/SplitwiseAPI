package com.splitwise.advanced.service.circle;

import com.splitwise.advanced.entities.circle.Circle;
import com.splitwise.advanced.entities.user.User;

import java.util.List;

public interface CircleService {

    void addUserFriendToCircle(User userOne, List<User> userTwo, Circle circle);

}
