package com.suvash.chirkutt.Service;

import com.suvash.chirkutt.Dto.Request.LoginDto;
import com.suvash.chirkutt.Dto.Request.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    boolean register(RegisterDto registerDto);
}