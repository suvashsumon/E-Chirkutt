package com.suvash.chirkutt.Dto.Response;

import lombok.Data;

import java.util.Date;

@Data
public class MessageResponseDto {
    private String message;
    private String senderinfo;
    private String status;
    private Date createdAt;

    public MessageResponseDto(String message, String senderinfo, String status, Date createdAt) {
        this.message = message;
        this.senderinfo = senderinfo;
        this.status = status;
        this.createdAt = createdAt;
    }
}
