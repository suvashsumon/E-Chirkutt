package com.suvash.chirkutt.Service.Impl;

import com.suvash.chirkutt.Dto.Response.LinkResponseDto;
import com.suvash.chirkutt.Service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Value("${app.custom.baseurl}")
    private String baseurl;

    @Override
    public LinkResponseDto getUserLink() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        LinkResponseDto linkResponseDto = new LinkResponseDto();
        linkResponseDto.setMessageLink(baseurl+"/send-chirkutt/"+username);
        return linkResponseDto;
    }
}
