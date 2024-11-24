package com.suvash.chirkutt.Controller;

import com.suvash.chirkutt.Dto.Request.PasswordChangeDto;
import com.suvash.chirkutt.Dto.Response.PasswordChangedResponse;
import com.suvash.chirkutt.Exceptions.UserNotFoundException;
import com.suvash.chirkutt.Model.User;
import com.suvash.chirkutt.Repository.UserRepository;
import com.suvash.chirkutt.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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
        userService.changePassword(passwordChangeDto);
        return new ResponseEntity<>(
                new PasswordChangedResponse(
                        "Password change successfully.", HttpStatus.OK.value()
                ),
                HttpStatus.OK);
    }
}
