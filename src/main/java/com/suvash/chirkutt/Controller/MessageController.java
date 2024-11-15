package com.suvash.chirkutt.Controller;

import com.suvash.chirkutt.Dto.MessageDto;
import com.suvash.chirkutt.Model.Message;
import com.suvash.chirkutt.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class MessageController {
    @Autowired
    MessageService messageService;

    @PostMapping("/api/send-chirkutt/")
    public ResponseEntity<String> sendChirkutt(@RequestBody MessageDto messageDto) {
        try {
            Message savedMessage = messageService.storeMessage(messageDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Message saved with ID: " + savedMessage.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while saving the message.");
        }
    }

}
