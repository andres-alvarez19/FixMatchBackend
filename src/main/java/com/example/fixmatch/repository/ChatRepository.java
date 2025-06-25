package com.example.fixmatch.repository;

import com.example.fixmatch.entity.Chat;
import com.example.fixmatch.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByUser1OrUser2(User user1, User user2);
    Optional<Chat> findByUser1AndUser2(User user1, User user2);
    Optional<Chat> findByUser2AndUser1(User user1, User user2); // for opposite order
}
