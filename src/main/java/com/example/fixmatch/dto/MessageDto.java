package com.example.fixmatch.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDto {
    private Long id;
    private Long chatId;
    private Long senderId;
    private String content;
    private LocalDateTime timestamp;
}
