package com.example.fixmatch.controller;

import com.example.fixmatch.dto.MessageRequest;
import com.example.fixmatch.dto.MessageDto;
import com.example.fixmatch.entity.Chat;
import com.example.fixmatch.entity.Message;
import com.example.fixmatch.entity.User;
import com.example.fixmatch.service.ChatService;
import com.example.fixmatch.service.MessageService;
import com.example.fixmatch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;
    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<MessageDto> send(@RequestBody MessageRequest request, Principal principal) {
        User sender = userService.findByEmail(principal.getName()).orElseThrow();
        User receiver;
        if (request.getChatId() != null) {
            Chat chat = chatService.findById(request.getChatId());
            receiver = chat.getUser1().equals(sender) ? chat.getUser2() : chat.getUser1();
        } else {
            receiver = userService.findById(request.getReceiverId()).orElseThrow();
        }
        Message message = messageService.send(request, sender, receiver);
        MessageDto dto = toDto(message);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<MessageDto>> messages(@RequestParam Long chatId, Pageable pageable, Principal principal) {
        chatService.findById(chatId); // ensure exists
        Chat chat = chatService.findById(chatId);
        Page<MessageDto> page = messageService.getMessages(chat, pageable)
                .map(this::toDto);
        return ResponseEntity.ok(page);
    }

    private MessageDto toDto(Message message) {
        MessageDto dto = new MessageDto();
        dto.setId(message.getId());
        dto.setChatId(message.getChat().getId());
        dto.setSenderId(message.getSender().getId());
        dto.setContent(message.getContent());
        dto.setTimestamp(message.getTimestamp());
        return dto;
    }
}
