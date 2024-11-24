package com.suvash.chirkutt.Controller;

import com.suvash.chirkutt.Dto.Request.PasswordChangeDto;
import com.suvash.chirkutt.Dto.Request.PasswordRecoveryDto;
import com.suvash.chirkutt.Dto.Response.PasswordChangedResponse;
import com.suvash.chirkutt.Repository.UserRepository;
import com.suvash.chirkutt.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password")
public class PasswordController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @PutMapping("/change")
    public ResponseEntity<?> changePassword(@Valid @RequestBody PasswordChangeDto passwordChangeDto)
    {
        return userService.changePassword(passwordChangeDto);
    }

    @PostMapping("/get-password-recovery-link")
    public ResponseEntity<?> getPasswordRecoveryLink(@Valid @RequestBody PasswordRecoveryDto passwordRecoveryDto)
    {
        return userService.passwordRecoveryLink(passwordRecoveryDto);
    }
}
