package com.splitwise.advanced.service.circle;

import com.splitwise.advanced.entities.circle.Circle;
import com.splitwise.advanced.entities.user.User;
import com.splitwise.advanced.entities.userfriend.UserFriend;
import com.splitwise.advanced.repository.CircleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CircleServiceImpl implements CircleService {

    @Autowired
    CircleRepository circleRepository;

    @Override
    public void addUserFriendToCircle(User userOne, List<User> userTwos, Circle circle) {

        for (User userTwo : userTwos) {
            UserFriend userFriend = userOne.getFriendsLinkedAsSmaller().stream().filter(uf -> uf.getBigger().getFullName().equals(userTwo.getFullName())).findFirst().orElse(null);

            if (userFriend == null) {
                userFriend = userOne.getFriendsLinkedAsBigger().stream().filter(uf -> uf.getSmaller().getFullName().equals(userTwo.getFullName())).findFirst().orElse(null);
            }

            if (userFriend != null) {
                circle.addUserFriend(userFriend);
            }
        }

        circleRepository.saveAndFlush(circle);
    }
}
