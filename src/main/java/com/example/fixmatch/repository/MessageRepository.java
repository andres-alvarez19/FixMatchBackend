package com.example.fixmatch.repository;

import com.example.fixmatch.entity.Message;
import com.example.fixmatch.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderAndReceiver(User sender, User receiver);
}
