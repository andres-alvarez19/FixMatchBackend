package com.example.fixmatch.repository;

import com.example.fixmatch.entity.Chat;
import com.example.fixmatch.entity.Message;
import com.example.fixmatch.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderAndReceiver(User sender, User receiver);
    List<Message> findByChat(Chat chat);
    Page<Message> findByChat(Chat chat, Pageable pageable);
}
