package com.suvash.chirkutt.Service.Impl;

import com.suvash.chirkutt.Dto.MessageDto;
import com.suvash.chirkutt.Model.Message;
import com.suvash.chirkutt.Model.User;
import com.suvash.chirkutt.Repository.MessageRepository;
import com.suvash.chirkutt.Repository.UserRepository;
import com.suvash.chirkutt.Service.MessageService;
import com.suvash.chirkutt.Service.UserService;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    MessageRepository messageRepository;

    @Override
    public Message storeMessage(MessageDto messageDto) {
        Optional<User> userOptional = userRepository.findByUsername(messageDto.getUsername());
        User user = userOptional.get();
        Message message = new Message();
        message.setUser(user);
        message.setMessage(messageDto.getMessage());
        message.setSenderinfo(messageDto.getSenderinfo());

        // Save to the database
        return messageRepository.save(message);
    }
}
