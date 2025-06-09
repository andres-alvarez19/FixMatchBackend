package com.example.fixmatch.controller;

import com.example.fixmatch.dto.MessageRequest;
import com.example.fixmatch.entity.Message;
import com.example.fixmatch.entity.User;
import com.example.fixmatch.service.MessageService;
import com.example.fixmatch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Message> send(@RequestBody MessageRequest request, Principal principal) {
        User sender = userService.findByEmail(principal.getName()).orElseThrow();
        User receiver = userService.findById(request.getReceiverId()).orElseThrow();
        return ResponseEntity.ok(messageService.send(request, sender, receiver));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Message>> chat(@PathVariable Long userId, Principal principal) {
        User sender = userService.findByEmail(principal.getName()).orElseThrow();
        User receiver = userService.findById(userId).orElseThrow();
        return ResponseEntity.ok(messageService.getChat(sender, receiver));
    }
}
