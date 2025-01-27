package com.splitwise.advanced.repository;

import com.splitwise.advanced.entities.userfriendcircle.UserFriendCircle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserFriendCircleRepository extends JpaRepository<UserFriendCircle, Integer> {

}
