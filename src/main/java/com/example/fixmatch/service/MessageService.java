package com.example.fixmatch.service;

import com.example.fixmatch.dto.MessageRequest;
import com.example.fixmatch.entity.Chat;
import com.example.fixmatch.entity.Message;
import com.example.fixmatch.entity.User;
import com.example.fixmatch.repository.MessageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatService chatService;

    public Message send(MessageRequest request, User sender, User receiver) {
        Chat chat;
        if (request.getChatId() != null) {
            chat = chatService.findById(request.getChatId());
        } else {
            chat = chatService.getOrCreateChat(sender, receiver);
        }
        Message m = new Message();
        m.setSender(sender);
        m.setReceiver(receiver);
        m.setChat(chat);
        m.setContent(request.getText() != null ? request.getText() : request.getFile());
        m.setTimestamp(LocalDateTime.now());
        return messageRepository.save(m);
    }

    public List<Message> getChat(User sender, User receiver) {
        Chat chat = chatService.getOrCreateChat(sender, receiver);
        return messageRepository.findByChat(chat);
    }

    public Page<Message> getMessages(Chat chat, Pageable pageable) {
        return messageRepository.findByChat(chat, pageable);
    }
}
