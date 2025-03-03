package com.splitwise.advanced.service.jwt;

import com.splitwise.advanced.entities.user.CustomUserDetails;
import com.splitwise.advanced.entities.user.User;
import com.splitwise.advanced.mapper.UserPopulator;
import com.splitwise.advanced.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    private final UserPopulator userPopulator;

    CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userPopulator = UserPopulator.INSTANCE;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = userRepository.findUserByEmail(username);

        return new CustomUserDetails(user);
    }
}
