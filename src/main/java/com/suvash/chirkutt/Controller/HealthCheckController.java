package com.suvash.chirkutt.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping("/health-check")
    public ResponseEntity<?> healthCheck()
    {
        return new ResponseEntity<>("System Running", HttpStatus.OK);
    }
}
