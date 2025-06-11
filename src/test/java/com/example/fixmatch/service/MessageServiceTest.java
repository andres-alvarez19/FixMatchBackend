package com.example.fixmatch.service;

import com.example.fixmatch.dto.MessageRequest;
import com.example.fixmatch.entity.Message;
import com.example.fixmatch.entity.User;
import com.example.fixmatch.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {
    @Mock
    private MessageRepository messageRepository;
    @InjectMocks
    private MessageService messageService;

    @Test
    void sendCreatesMessageWithTimestamp() {
        MessageRequest req = new MessageRequest();
        req.setContent("hello");
        User sender = new User();
        User receiver = new User();

        when(messageRepository.save(any(Message.class))).thenAnswer(inv -> inv.getArgument(0));

        Message m = messageService.send(req, sender, receiver);

        assertEquals("hello", m.getContent());
        assertEquals(sender, m.getSender());
        assertEquals(receiver, m.getReceiver());
        assertNotNull(m.getTimestamp());
        verify(messageRepository).save(any(Message.class));
    }

    @Test
    void getChatReturnsMessages() {
        User sender = new User();
        User receiver = new User();
        List<Message> messages = List.of(new Message());
        when(messageRepository.findBySenderAndReceiver(sender, receiver)).thenReturn(messages);

        List<Message> result = messageService.getChat(sender, receiver);

        assertEquals(messages, result);
        verify(messageRepository).findBySenderAndReceiver(sender, receiver);
    }
}
