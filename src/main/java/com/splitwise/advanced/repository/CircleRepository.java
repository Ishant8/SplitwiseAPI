package com.splitwise.advanced.repository;

import com.splitwise.advanced.entities.circle.Circle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CircleRepository extends JpaRepository<Circle, Integer> {

    Circle findByName(String name);

}
