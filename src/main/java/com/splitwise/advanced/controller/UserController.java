package com.splitwise.advanced.controller;

import com.splitwise.advanced.entities.preference.Preference;
import com.splitwise.advanced.entities.user.User;
import com.splitwise.advanced.entities.userfriend.UserFriend;
import com.splitwise.advanced.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/friend")
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

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
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

    @PutMapping("/unfriend")
    public User unFriend(@RequestParam String userName, @RequestParam String friendName) {
        User user = userRepository.findByFullName(userName);
        User friend = userRepository.findByFullName(friendName);

        if(user.getId() > friend.getId()) {

//            //Initial Approach, iterates the list twice.
//            UserFriend userFriend = user.getFriendsLinkedAsSmaller().stream().filter(uf -> uf.getSmaller().getId() == friend.getId()).findFirst().orElse(null);
//            user.getFriendsLinkedAsSmaller().remove(userFriend);

//            // We can also use iterator. Its a good appraoch
//            Iterator<UserFriend> iterator = friendsList.iterator();
//            UserFriend userFriend = null;
//            while (iterator.hasNext()) {
//                UserFriend uf = iterator.next();
//                if (uf.getSmaller().getId() == friend.getId()) {
//                    userFriend = uf;
//                    iterator.remove();
//                    break;
//                }
//            }

            //right approach for what I want.

            user.getFriendsLinkedAsBigger().removeIf(uf -> uf.getSmaller().getId() == friend.getId());
            friend.getFriendsLinkedAsSmaller().removeIf(uf -> uf.getBigger().getId() == user.getId());
        }
        else {
            user.getFriendsLinkedAsSmaller().removeIf(uf -> uf.getBigger().getId() == friend.getId());
            friend.getFriendsLinkedAsBigger().removeIf(uf -> uf.getSmaller().getId() == user.getId());
        }

        System.out.println(user);

        return userRepository.save(user);
    }

//    @PutMapping("/preference/update")
//    public
}
