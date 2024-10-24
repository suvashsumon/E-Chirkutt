package com.suvash.chirkutt.Controller;

import com.suvash.chirkutt.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/api/user/")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/get-link")
    public ResponseEntity<?>index()
    {
        return new ResponseEntity<>(userService.getUserLink(), HttpStatus.OK);
    }
}
