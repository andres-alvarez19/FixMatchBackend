package com.example.fixmatch.service;

import com.example.fixmatch.entity.Chat;
import com.example.fixmatch.entity.User;
import com.example.fixmatch.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    public Chat getOrCreateChat(User user1, User user2) {
        return chatRepository.findByUser1AndUser2(user1, user2)
                .or(() -> chatRepository.findByUser2AndUser1(user1, user2))
                .orElseGet(() -> {
                    Chat chat = new Chat();
                    chat.setUser1(user1);
                    chat.setUser2(user2);
                    return chatRepository.save(chat);
                });
    }

    public List<Chat> findByUser(User user) {
        return chatRepository.findByUser1OrUser2(user, user);
    }

    public Chat findById(Long id) {
        return chatRepository.findById(id).orElseThrow();
    }

    public void delete(Long id) {
        chatRepository.deleteById(id);
    }
}
