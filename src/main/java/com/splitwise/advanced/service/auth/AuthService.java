package com.splitwise.advanced.service.auth;

import com.splitwise.advanced.dto.request.LoginDto;

public interface AuthService {
    String verify(LoginDto loginDto);
}
