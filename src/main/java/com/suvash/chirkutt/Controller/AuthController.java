package com.suvash.chirkutt.Controller;

import com.suvash.chirkutt.Dto.Response.AuthResponseDto;
import com.suvash.chirkutt.Dto.Request.LoginDto;
import com.suvash.chirkutt.Dto.Request.RegisterDto;
import com.suvash.chirkutt.Repository.UserRepository;
import com.suvash.chirkutt.Service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    UserRepository userRepository;

    // Build Login REST API
    @PostMapping("/login")
    @Operation(
            summary = "Login Request",
            description = "This endpoint checks user credentials and then return JWT token if user gets authenticated."
    )
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginDto loginDto){

        //01 - Receive the token from AuthService
        String token = authService.login(loginDto);

        //02 - Set the token as a response using JwtAuthResponse Dto class
        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setAccessToken(token);

        //03 - Return the response to the user
        return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDto)
    {
        if(userRepository.existsByUsername(registerDto.getUsername()))
            return new ResponseEntity<>("Duplicate username found.", HttpStatus.BAD_REQUEST);

        if(userRepository.existsByEmail(registerDto.getEmail()))
            return new ResponseEntity<>("Duplicate email found.", HttpStatus.BAD_REQUEST);

        boolean flag = authService.register(registerDto);
        if(flag)
        {
            LoginDto loginDto = new LoginDto();
            loginDto.setUsername(registerDto.getUsername());
            loginDto.setPassword(registerDto.getPassword());
            String token = authService.login(loginDto);
            AuthResponseDto authResponseDto = new AuthResponseDto();
            authResponseDto.setAccessToken(token);
            return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
        }
        return new ResponseEntity<>("User account registration failed.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}