package com.suvash.chirkutt.Service.Impl;

import com.suvash.chirkutt.Dto.Request.PasswordChangeDto;
import com.suvash.chirkutt.Dto.Request.PasswordRecoveryDto;
import com.suvash.chirkutt.Dto.Request.ResetNewPasswordDto;
import com.suvash.chirkutt.Dto.Response.CustomResponseDto;
import com.suvash.chirkutt.Dto.Response.LinkResponseDto;
import com.suvash.chirkutt.Dto.Response.MessageResponseDto;
import com.suvash.chirkutt.Dto.Response.PasswordChangedResponse;
import com.suvash.chirkutt.Exceptions.UserNotFoundException;
import com.suvash.chirkutt.Model.Message;
import com.suvash.chirkutt.Model.User;
import com.suvash.chirkutt.Repository.MessageRepository;
import com.suvash.chirkutt.Repository.UserRepository;
import com.suvash.chirkutt.Service.EmailService;
import com.suvash.chirkutt.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Value("${app.custom.baseurl}")
    private String baseurl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;


    public Authentication getAuthenticatedUserDetails()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

    @Override
    public LinkResponseDto getUserLink() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = getAuthenticatedUserDetails().getName();
        LinkResponseDto linkResponseDto = new LinkResponseDto();
        linkResponseDto.setMessageLink(baseurl+"/send-chirkutt/"+username);
        return linkResponseDto;
    }

    @Override
    public List<MessageResponseDto> getAllMessage() {
        // fetch the user
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = getAuthenticatedUserDetails().getName();
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

    public ResponseEntity<?> changePassword(PasswordChangeDto passwordChangeDto)
    {
        Optional<User> userOpt = userRepository.findByUsername(this.getAuthenticatedUserDetails().getName());
        if(!userOpt.isPresent())
        {
            throw new UserNotFoundException("User not found.");
        }

        User user = userOpt.get();
        user.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
        userRepository.save(user);

        return new ResponseEntity<>(
                new PasswordChangedResponse(
                        "Password change successfully.", HttpStatus.OK.value()
                ),
                HttpStatus.OK
        );
    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public ResponseEntity<?> passwordRecoveryLink(PasswordRecoveryDto passwordRecoveryDto)
    {
        // check that user exist or not
        Optional<User> userOpt = userRepository.findByUsername(passwordRecoveryDto.getUsername());
        if(!userOpt.isPresent())
            throw new UserNotFoundException("User not found.");
        User user = userOpt.get();
        String userEmail = user.getEmail();
        String recoveryToken = generateToken();
        String emailBody = "Password Recovery Token : "+recoveryToken;
        emailService.sendEmailToUser(userEmail, "Password Recovery Token", emailBody);

        user.setPasswordRecoveryToken(recoveryToken);
        user.setTokenExpiryTime(new Date());
        userRepository.save(user);

        return new ResponseEntity<>(
                new CustomResponseDto(
                        "Password recovery token has been sent.",
                        HttpStatus.OK.value()
                ),
                HttpStatus.OK
        );
    }

    public ResponseEntity<?> recoverPasswordService(ResetNewPasswordDto resetNewPasswordDto)
    {
        Optional<User> userOpt = userRepository.findByUsername(resetNewPasswordDto.getUsername());
        if(!userOpt.isPresent())
        {
            throw new UserNotFoundException("User not found");
        }

        User user = userOpt.get();

        if(user.getTokenExpiryTime().before(new Date()))
        {
            return new ResponseEntity<>(
                    new CustomResponseDto("Token expired.", HttpStatus.BAD_REQUEST.value()),
                    HttpStatus.BAD_REQUEST
            );
        }

        if(!user.getPasswordRecoveryToken().equals(resetNewPasswordDto.getToken()))
        {
            return new ResponseEntity<>(
                    new CustomResponseDto("Invalid token provided.", HttpStatus.BAD_REQUEST.value()),
                    HttpStatus.BAD_REQUEST
            );
        }

        user.setPassword(passwordEncoder.encode(resetNewPasswordDto.getNewPassword()));
        user.setTokenExpiryTime(null);
        user.setPasswordRecoveryToken(null);
        userRepository.save(user);
        return new ResponseEntity<>(
                new CustomResponseDto("Password changed successfully.", HttpStatus.OK.value()),
                HttpStatus.OK
        );
    }
}
