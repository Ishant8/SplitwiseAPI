package com.splitwise.advanced.repository;

import com.splitwise.advanced.entities.userfriend.UserFriend;
import com.splitwise.advanced.entities.userfriend.UserFriendId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFriendRepository extends JpaRepository<UserFriend, Integer> {

    UserFriend findBySmaller_IdAndBigger_Id(int smallerId, int biggerId);

}
