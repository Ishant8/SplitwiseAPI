package com.splitwise.advanced.controller;

import com.splitwise.advanced.dto.response.CircleRespDto;
import com.splitwise.advanced.entities.circle.Circle;
import com.splitwise.advanced.entities.user.User;
import com.splitwise.advanced.entities.usercircle.UserCircle;
import com.splitwise.advanced.entities.userfriend.UserFriend;
import com.splitwise.advanced.mapper.CirclePopulator;
import com.splitwise.advanced.repository.CircleRepository;
import com.splitwise.advanced.repository.UserRepository;
import com.splitwise.advanced.service.circle.CircleService;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/circle")
public class CircleController {

    private final CircleRepository circleRepository;
    private final UserRepository userRepository;
    private final CircleService circleService;

    public CircleController(CircleRepository circleRepository, UserRepository userRepository, CircleService circleService) {
        this.circleRepository = circleRepository;
        this.userRepository = userRepository;
        this.circleService = circleService;
    }

    @PostMapping("/add")
    public Circle addCircle(@RequestBody Circle circle) {
        return circleRepository.save(circle);
    }

    @PutMapping("/add/user")
    public User addUserToCircle(@RequestParam String userName, @RequestParam String circleName) {

        User user = userRepository.findByFullName(userName);
        Circle circle = circleRepository.findByName(circleName);

        List<User> members = circle.getUsers().stream().map(UserCircle::getUser).toList();
        List<User> friendsOfUser = user.getFriends();
        List<User> notFriends = new ArrayList<>();

        members.forEach(member -> {
            if(!friendsOfUser.contains(member)) {
                user.addFriend(member);
                notFriends.add(member);
            }
        });


        user.addCircle(circle);

        User savedUser = userRepository.saveAndFlush(user);
        circleService.addUserFriendToCircle(user, members, circle);

        return savedUser;

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
        User userTwo = userRepository.findByFullName(friendTwo);

        circleService.addUserFriendToCircle(userOne, Collections.singletonList(userTwo),circle);


//        UserFriend userFriend = userOne.getFriendsLinkedAsSmaller().stream().filter(uf -> uf.getBigger().getFullName().equals(friendTwo)).findFirst().orElse(null);
//
//        if(userFriend == null) {
//            userFriend = userOne.getFriendsLinkedAsBigger().stream().filter(uf -> uf.getSmaller().getFullName().equals(friendTwo)).findFirst().orElse(null);
//        }
//
//        if(userFriend != null) {
//            circle.addUserFriend(userFriend);
//            return circleRepository.save(circle);
//        }

        return null;

    }

    @GetMapping("/get/all")
    public List<CircleRespDto> getAllCircles(){
        List<Circle> circles = circleRepository.findAll();
        return circles.stream().map(circle -> CirclePopulator.INSTANCE.populateCircle(circle)).toList();
    }

    @GetMapping("/get/members")
    public List<User> getCircleMembers(@RequestParam String groupName) {
        Circle circle = circleRepository.findByName(groupName);
        return circle.getUsers().stream().map(UserCircle::getUser).toList();
    }

}
