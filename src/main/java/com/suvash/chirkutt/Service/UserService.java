package com.suvash.chirkutt.Service;

import com.suvash.chirkutt.Dto.Request.PasswordChangeDto;
import com.suvash.chirkutt.Dto.Request.PasswordRecoveryDto;
import com.suvash.chirkutt.Dto.Request.ResetNewPasswordDto;
import com.suvash.chirkutt.Dto.Response.LinkResponseDto;
import com.suvash.chirkutt.Dto.Response.MessageResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    LinkResponseDto getUserLink();
    List<MessageResponseDto> getAllMessage();
    Authentication getAuthenticatedUserDetails();
    ResponseEntity<?> changePassword(PasswordChangeDto passwordChangeDto);
    ResponseEntity<?> passwordRecoveryLink(PasswordRecoveryDto passwordRecoveryDto);
    ResponseEntity<?> recoverPasswordService(ResetNewPasswordDto resetNewPasswordDto);
}
