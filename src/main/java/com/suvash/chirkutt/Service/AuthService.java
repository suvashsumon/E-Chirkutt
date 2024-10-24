package com.suvash.chirkutt.Service;

import com.suvash.chirkutt.Dto.LoginDto;
import com.suvash.chirkutt.Dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    boolean register(RegisterDto registerDto);
}