package com.splitwise.advanced.controller;

import com.splitwise.advanced.entities.circle.Circle;
import com.splitwise.advanced.entities.user.User;
import com.splitwise.advanced.entities.userfriend.UserFriend;
import com.splitwise.advanced.repository.CircleRepository;
import com.splitwise.advanced.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/circle")
public class CircleController {

    private final CircleRepository circleRepository;
    private final UserRepository userRepository;

    public CircleController(CircleRepository circleRepository, UserRepository userRepository) {
        this.circleRepository = circleRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/add")
    public Circle addCircle(@RequestBody Circle circle) {
        return circleRepository.save(circle);
    }

    @PutMapping("/add/user")
    public User addUserToCircle(@RequestParam String userName, @RequestParam String circleName) {

        User user = userRepository.findByFullName(userName);
        Circle circle = circleRepository.findByName(circleName);
        user.addCircle(circle);

        return userRepository.save(user);
    }

    @GetMapping("/get")
    public Circle getCircle(@RequestParam String circleName) {
        Circle circle = circleRepository.findByName(circleName);
        System.out.println(circle);
        return circle;
    }

    @PutMapping("/remove/user")
    public Circle deleteUserFromCircle(@RequestParam String userName, @RequestParam String circleName) {
        Circle circle = circleRepository.findByName(circleName);
        circle.removeUser(userName);

        return circleRepository.save(circle);
    }

    @DeleteMapping("/delete")
    public String deleteCircle(@RequestParam String circleName) {
        Circle circle = circleRepository.findByName(circleName);
        circleRepository.delete(circle);
        return circleName + " has been deleted";
    }

    @PutMapping("/friends/add")
    public Circle addFriends(@RequestParam String friendOne,@RequestParam String friendTwo, @RequestParam String circleName) {
        Circle circle = circleRepository.findByName(circleName);
        User userOne = userRepository.findByFullName(friendOne);
        UserFriend userFriend = userOne.getFriendsLinkedAsSmaller().stream().filter(uf -> uf.getBigger().getFullName().equals(friendTwo)).findFirst().orElse(null);

        if(userFriend == null) {
            userFriend = userOne.getFriendsLinkedAsBigger().stream().filter(uf -> uf.getSmaller().getFullName().equals(friendTwo)).findFirst().orElse(null);
        }

        if(userFriend != null) {
            circle.addUserFriend(userFriend);
            return circleRepository.save(circle);
        }

        return null;

    }

}
