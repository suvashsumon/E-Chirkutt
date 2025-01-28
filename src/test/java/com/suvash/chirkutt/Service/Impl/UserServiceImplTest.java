package com.suvash.chirkutt.Service.Impl;

import com.suvash.chirkutt.Dto.Request.PasswordChangeDto;
import com.suvash.chirkutt.Dto.Request.PasswordRecoveryDto;
import com.suvash.chirkutt.Dto.Request.ResetNewPasswordDto;
import com.suvash.chirkutt.Dto.Response.LinkResponseDto;
import com.suvash.chirkutt.Dto.Response.MessageResponseDto;
import com.suvash.chirkutt.Exceptions.UserNotFoundException;
import com.suvash.chirkutt.Model.Message;
import com.suvash.chirkutt.Model.User;
import com.suvash.chirkutt.Repository.MessageRepository;
import com.suvash.chirkutt.Repository.UserRepository;
import com.suvash.chirkutt.Service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private MessageRepository mockMessageRepository;
    @Mock
    private PasswordEncoder mockPasswordEncoder;
    @Mock
    private EmailService mockEmailService;

    @InjectMocks
    private UserServiceImpl userServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(userServiceImplUnderTest, "baseurl", "baseurl");
    }

    @Test
    void testGetAuthenticatedUserDetails() {
        // Setup
        final Authentication expectedResult = new TestingAuthenticationToken("user", "pass", "ROLE_USER");

        // Run the test
        final Authentication result = userServiceImplUnderTest.getAuthenticatedUserDetails();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetUserLink() {
        // Setup
        final LinkResponseDto expectedResult = new LinkResponseDto();
        expectedResult.setMessageLink("messageLink");

        // Run the test
        final LinkResponseDto result = userServiceImplUnderTest.getUserLink();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetAllMessage() {
        // Setup
        final List<MessageResponseDto> expectedResult = List.of(
                new MessageResponseDto("message", "senderinfo", "status",
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime()));

        // Configure UserRepository.findByUsername(...).
        final User user1 = new User();
        user1.setId(0L);
        user1.setEmail("email");
        user1.setPassword("password");
        user1.setPasswordRecoveryToken("passwordRecoveryToken");
        user1.setTokenExpiryTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findByUsername("username")).thenReturn(user);

        // Configure MessageRepository.findByUser(...).
        final Message message = new Message();
        message.setId(0L);
        message.setSenderinfo("senderinfo");
        message.setMessage("message");
        message.setStatus("status");
        message.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<Message> messages = List.of(message);
        final User user2 = new User();
        user2.setId(0L);
        user2.setEmail("email");
        user2.setPassword("password");
        user2.setPasswordRecoveryToken("passwordRecoveryToken");
        user2.setTokenExpiryTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        when(mockMessageRepository.findByUser(user2)).thenReturn(messages);

        // Run the test
        final List<MessageResponseDto> result = userServiceImplUnderTest.getAllMessage();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetAllMessage_UserRepositoryReturnsAbsent() {
        // Setup
        final List<MessageResponseDto> expectedResult = List.of(
                new MessageResponseDto("message", "senderinfo", "status",
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime()));
        when(mockUserRepository.findByUsername("username")).thenReturn(Optional.empty());

        // Configure MessageRepository.findByUser(...).
        final Message message = new Message();
        message.setId(0L);
        message.setSenderinfo("senderinfo");
        message.setMessage("message");
        message.setStatus("status");
        message.setCreatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<Message> messages = List.of(message);
        final User user = new User();
        user.setId(0L);
        user.setEmail("email");
        user.setPassword("password");
        user.setPasswordRecoveryToken("passwordRecoveryToken");
        user.setTokenExpiryTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        when(mockMessageRepository.findByUser(user)).thenReturn(messages);

        // Run the test
        final List<MessageResponseDto> result = userServiceImplUnderTest.getAllMessage();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetAllMessage_MessageRepositoryReturnsNoItems() {
        // Setup
        // Configure UserRepository.findByUsername(...).
        final User user1 = new User();
        user1.setId(0L);
        user1.setEmail("email");
        user1.setPassword("password");
        user1.setPasswordRecoveryToken("passwordRecoveryToken");
        user1.setTokenExpiryTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findByUsername("username")).thenReturn(user);

        // Configure MessageRepository.findByUser(...).
        final User user2 = new User();
        user2.setId(0L);
        user2.setEmail("email");
        user2.setPassword("password");
        user2.setPasswordRecoveryToken("passwordRecoveryToken");
        user2.setTokenExpiryTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        when(mockMessageRepository.findByUser(user2)).thenReturn(Collections.emptyList());

        // Run the test
        final List<MessageResponseDto> result = userServiceImplUnderTest.getAllMessage();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testChangePassword() {
        // Setup
        final PasswordChangeDto passwordChangeDto = new PasswordChangeDto();
        passwordChangeDto.setNewPassword("newPassword");

        final ResponseEntity<?> expectedResult = new ResponseEntity<>(null, HttpStatus.OK);

        // Configure UserRepository.findByUsername(...).
        final User user1 = new User();
        user1.setId(0L);
        user1.setEmail("email");
        user1.setPassword("password");
        user1.setPasswordRecoveryToken("passwordRecoveryToken");
        user1.setTokenExpiryTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findByUsername("username")).thenReturn(user);

        when(mockPasswordEncoder.encode("newPassword")).thenReturn("password");

        // Run the test
        final ResponseEntity<?> result = userServiceImplUnderTest.changePassword(passwordChangeDto);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);

        // Confirm UserRepository.save(...).
        final User entity = new User();
        entity.setId(0L);
        entity.setEmail("email");
        entity.setPassword("password");
        entity.setPasswordRecoveryToken("passwordRecoveryToken");
        entity.setTokenExpiryTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        verify(mockUserRepository).save(entity);
    }

    @Test
    void testChangePassword_UserRepositoryFindByUsernameReturnsAbsent() {
        // Setup
        final PasswordChangeDto passwordChangeDto = new PasswordChangeDto();
        passwordChangeDto.setNewPassword("newPassword");

        when(mockUserRepository.findByUsername("username")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> userServiceImplUnderTest.changePassword(passwordChangeDto))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void testGenerateToken() {
        assertThat(userServiceImplUnderTest.generateToken()).isEqualTo("2c117ec6-54ec-4e63-9a6b-29e705426d88");
    }

    @Test
    void testPasswordRecoveryLink() {
        // Setup
        final PasswordRecoveryDto passwordRecoveryDto = new PasswordRecoveryDto();
        passwordRecoveryDto.setUsername("username");

        final ResponseEntity<?> expectedResult = new ResponseEntity<>(null, HttpStatus.OK);

        // Configure UserRepository.findByUsername(...).
        final User user1 = new User();
        user1.setId(0L);
        user1.setEmail("email");
        user1.setPassword("password");
        user1.setPasswordRecoveryToken("passwordRecoveryToken");
        user1.setTokenExpiryTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findByUsername("username")).thenReturn(user);

        // Run the test
        final ResponseEntity<?> result = userServiceImplUnderTest.passwordRecoveryLink(passwordRecoveryDto);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockEmailService).sendEmailToUser("email", "Password Recovery Token", "body");

        // Confirm UserRepository.save(...).
        final User entity = new User();
        entity.setId(0L);
        entity.setEmail("email");
        entity.setPassword("password");
        entity.setPasswordRecoveryToken("passwordRecoveryToken");
        entity.setTokenExpiryTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        verify(mockUserRepository).save(entity);
    }

    @Test
    void testPasswordRecoveryLink_UserRepositoryFindByUsernameReturnsAbsent() {
        // Setup
        final PasswordRecoveryDto passwordRecoveryDto = new PasswordRecoveryDto();
        passwordRecoveryDto.setUsername("username");

        when(mockUserRepository.findByUsername("username")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> userServiceImplUnderTest.passwordRecoveryLink(passwordRecoveryDto))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void testRecoverPasswordService() {
        // Setup
        final ResetNewPasswordDto resetNewPasswordDto = new ResetNewPasswordDto();
        resetNewPasswordDto.setUsername("username");
        resetNewPasswordDto.setNewPassword("newPassword");
        resetNewPasswordDto.setToken("token");

        final ResponseEntity<?> expectedResult = new ResponseEntity<>(null, HttpStatus.OK);

        // Configure UserRepository.findByUsername(...).
        final User user1 = new User();
        user1.setId(0L);
        user1.setEmail("email");
        user1.setPassword("password");
        user1.setPasswordRecoveryToken("passwordRecoveryToken");
        user1.setTokenExpiryTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findByUsername("username")).thenReturn(user);

        when(mockPasswordEncoder.encode("newPassword")).thenReturn("password");

        // Run the test
        final ResponseEntity<?> result = userServiceImplUnderTest.recoverPasswordService(resetNewPasswordDto);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);

        // Confirm UserRepository.save(...).
        final User entity = new User();
        entity.setId(0L);
        entity.setEmail("email");
        entity.setPassword("password");
        entity.setPasswordRecoveryToken("passwordRecoveryToken");
        entity.setTokenExpiryTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        verify(mockUserRepository).save(entity);
    }

    @Test
    void testRecoverPasswordService_UserRepositoryFindByUsernameReturnsAbsent() {
        // Setup
        final ResetNewPasswordDto resetNewPasswordDto = new ResetNewPasswordDto();
        resetNewPasswordDto.setUsername("username");
        resetNewPasswordDto.setNewPassword("newPassword");
        resetNewPasswordDto.setToken("token");

        when(mockUserRepository.findByUsername("username")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> userServiceImplUnderTest.recoverPasswordService(resetNewPasswordDto))
                .isInstanceOf(UserNotFoundException.class);
    }
}
