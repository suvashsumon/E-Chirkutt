package com.suvash.chirkutt.Dto.Response;

import lombok.Data;

import java.util.Date;

@Data
public class MessageResponseDto {
    private String message;
    private String senderinfo;
    private Date createdAt;

    public MessageResponseDto(String message, String senderinfo, Date createdAt) {
        this.message = message;
        this.senderinfo = senderinfo;
        this.createdAt = createdAt;
    }
}
