package com.suvash.chirkutt.Service;

import com.suvash.chirkutt.Dto.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}