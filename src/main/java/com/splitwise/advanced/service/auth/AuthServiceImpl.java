package com.splitwise.advanced.service.auth;

import com.splitwise.advanced.dto.request.LoginDto;
import com.splitwise.advanced.entities.jwt.JwtToken;
import com.splitwise.advanced.service.jwt.JWTService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    AuthenticationManager authManager;
    JWTService jwtService;

    AuthServiceImpl(AuthenticationManager authManager, JWTService jwtService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    @Override
    public String verify(LoginDto loginDto) {

        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

        if(authentication.isAuthenticated()){
            return new JwtToken(jwtService.generateToken(10,loginDto.getUsername())).getToken() ;
        }else {
            return "Fail";
        }
    }
}
