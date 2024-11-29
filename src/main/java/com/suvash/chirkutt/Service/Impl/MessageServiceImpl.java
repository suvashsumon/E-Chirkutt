package com.suvash.chirkutt.Service.Impl;

import com.suvash.chirkutt.Dto.Request.MessageDto;
import com.suvash.chirkutt.Model.Keyword;
import com.suvash.chirkutt.Model.Message;
import com.suvash.chirkutt.Model.User;
import com.suvash.chirkutt.Repository.KeywordRepository;
import com.suvash.chirkutt.Repository.MessageRepository;
import com.suvash.chirkutt.Repository.UserRepository;
import com.suvash.chirkutt.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    KeywordRepository keywordRepository;

    @Override
    public Message storeMessage(MessageDto messageDto) {
        Optional<User> userOptional = userRepository.findByUsername(messageDto.getUsername());
        User user = userOptional.get();

        List<Keyword> keywords = keywordRepository.findByUser(user);

        Message message = new Message();
        message.setUser(user);
        message.setMessage(messageDto.getMessage());
        message.setSenderinfo(messageDto.getSenderinfo());

        // Check if any keyword is present in the message
        boolean containsKeyword = keywords.stream()
                .map(Keyword::getKeyword)
                .anyMatch(keyword -> messageDto.getMessage().contains(keyword));

        // Set the status based on the presence of a keyword
        if (containsKeyword) {
            message.setStatus("blocked");
        } else {
            message.setStatus("passed");
        }

        // Save to the database
        return messageRepository.save(message);
    }
}
