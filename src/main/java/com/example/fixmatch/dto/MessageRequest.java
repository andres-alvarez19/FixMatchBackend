package com.example.fixmatch.dto;

import lombok.Data;

@Data
public class MessageRequest {
    private Long chatId;
    private Long receiverId;
    private String text;
    private String file;
}
