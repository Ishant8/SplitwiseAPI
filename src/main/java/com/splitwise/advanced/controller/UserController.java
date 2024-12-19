package com.splitwise.advanced.controller;

import com.splitwise.advanced.entities.preference.Preference;
import com.splitwise.advanced.entities.user.User;
import com.splitwise.advanced.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/add")
    public User addUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @PostMapping("/friend")
    public User addFriend(@RequestParam String friendName, @RequestParam String fullName) {
        User user = userRepository.findByFullName(fullName);
        User friend = userRepository.findByFullName(friendName);
        System.out.println(user);
        System.out.println(friend);
        if(friend != null && user != null ) {
            user.addFriend(friend);
            return userRepository.save(user);
        }

        return null;

    }

    @GetMapping("/get")
    public User getUser(@RequestParam String userName) {

        User user = userRepository.findByFullName(userName);
        System.out.println(user);

        return user;
    }

    @PutMapping("/preference")
    public User updatePreference(@RequestParam String userName, @RequestBody Preference preference){
        User user = userRepository.findByFullName(userName);
        preference.setId(user.getId());
        user.setPreference(preference);
        return userRepository.save(user);
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam String userName) {
        User user = userRepository.findByFullName(userName);
        userRepository.delete(user);
        return "Deleted";
    }
}
