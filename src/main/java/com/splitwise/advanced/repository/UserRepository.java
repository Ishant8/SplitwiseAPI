package com.splitwise.advanced.repository;

import com.splitwise.advanced.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByFullName(String fullName);
}
