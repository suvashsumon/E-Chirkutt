package com.suvash.chirkutt.Service.Impl;

import com.suvash.chirkutt.Dto.Response.LinkResponseDto;
import com.suvash.chirkutt.Dto.Response.MessageResponseDto;
import com.suvash.chirkutt.Model.Message;
import com.suvash.chirkutt.Model.User;
import com.suvash.chirkutt.Repository.MessageRepository;
import com.suvash.chirkutt.Repository.UserRepository;
import com.suvash.chirkutt.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Value("${app.custom.baseurl}")
    private String baseurl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public LinkResponseDto getUserLink() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        LinkResponseDto linkResponseDto = new LinkResponseDto();
        linkResponseDto.setMessageLink(baseurl+"/send-chirkutt/"+username);
        return linkResponseDto;
    }

    @Override
    public List<MessageResponseDto> getAllMessage() {
        // fetch the user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = null;
        if(optionalUser.isPresent()) user = optionalUser.get();

        // fetch the messages
        List<Message> messages = messageRepository.findByUser(user).stream()
                .sorted((m1, m2) -> m2.getCreatedAt().compareTo(m1.getCreatedAt())) // Descending order
                .toList();

        // convert Message list into MessageResponseDto list
        return messages.stream().map(message -> new MessageResponseDto(
                message.getMessage(),
                message.getSenderinfo(),
                message.getCreatedAt())
        ).collect(Collectors.toList());
    }


}
