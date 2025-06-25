package com.example.fixmatch.controller;

import com.example.fixmatch.dto.ChatDto;
import com.example.fixmatch.dto.ChatUserDto;
import com.example.fixmatch.entity.Chat;
import com.example.fixmatch.entity.User;
import com.example.fixmatch.service.ChatService;
import com.example.fixmatch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<ChatDto>> list(Principal principal) {
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        List<ChatDto> chats = chatService.findByUser(user).stream().map(c -> {
            ChatDto dto = new ChatDto();
            dto.setId(c.getId());
            User other = c.getUser1().equals(user) ? c.getUser2() : c.getUser1();
            dto.setUser(new ChatUserDto(other.getId(), other.getName()));
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(chats);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<ChatUserDto> get(@PathVariable Long chatId, Principal principal) {
        User current = userService.findByEmail(principal.getName()).orElseThrow();
        Chat chat = chatService.findById(chatId);
        User other = chat.getUser1().equals(current) ? chat.getUser2() : chat.getUser1();
        return ResponseEntity.ok(new ChatUserDto(other.getId(), other.getName()));
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<Void> delete(@PathVariable Long chatId) {
        chatService.delete(chatId);
        return ResponseEntity.ok().build();
    }
}
