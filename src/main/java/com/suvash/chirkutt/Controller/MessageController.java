package com.suvash.chirkutt.Controller;

import com.suvash.chirkutt.Dto.Request.MessageDto;
import com.suvash.chirkutt.Dto.Response.MessageSentResponse;
import com.suvash.chirkutt.Exceptions.UserNotFoundException;
import com.suvash.chirkutt.Model.User;
import com.suvash.chirkutt.Repository.UserRepository;
import com.suvash.chirkutt.Service.MessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping
public class MessageController {
    @Autowired
    MessageService messageService;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/api/send-chirkutt/")
    public ResponseEntity<?> sendChirkutt(@Valid @RequestBody MessageDto messageDto) {
        Optional<User> user = userRepository.findByUsername(messageDto.getUsername());
        if(user.isEmpty()) throw new UserNotFoundException("User not found.");
        messageService.storeMessage(messageDto);
        MessageSentResponse response = new MessageSentResponse(HttpStatus.CREATED, "Message has sent successfully.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
