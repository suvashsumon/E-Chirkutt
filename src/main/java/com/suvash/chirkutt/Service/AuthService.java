package com.suvash.chirkutt.Service;

import com.suvash.chirkutt.Dto.Request.LoginDto;
import com.suvash.chirkutt.Dto.Request.PasswordChangeDto;
import com.suvash.chirkutt.Dto.Request.RegisterDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    String login(LoginDto loginDto);
    boolean register(RegisterDto registerDto);
}