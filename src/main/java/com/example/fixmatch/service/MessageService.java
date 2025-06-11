package com.example.fixmatch.service;

import com.example.fixmatch.dto.MessageRequest;
import com.example.fixmatch.entity.Message;
import com.example.fixmatch.entity.User;
import com.example.fixmatch.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public Message send(MessageRequest request, User sender, User receiver) {
        Message m = new Message();
        m.setSender(sender);
        m.setReceiver(receiver);
        m.setContent(request.getContent());
        m.setTimestamp(LocalDateTime.now());
        return messageRepository.save(m);
    }

    public List<Message> getChat(User sender, User receiver) {
        return messageRepository.findBySenderAndReceiver(sender, receiver);
    }
}
