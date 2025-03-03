package com.splitwise.advanced.controller;

import com.splitwise.advanced.dto.request.LoginDto;
import com.splitwise.advanced.service.auth.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    AuthService authService;

    @Autowired
    AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto, HttpServletResponse response) {

        String jwtToken = authService.verify(loginDto);

        if(jwtToken != null)
        {
            Cookie cookie = new Cookie("JwtToken", jwtToken);
            cookie.setPath("/"); // Set the same path as the original cookie
            cookie.setMaxAge(60*30); // Set maxAge to 0 to delete the cookie
            response.addCookie(cookie);

            return jwtToken;
        }

        return "Login failed";
    }

}
