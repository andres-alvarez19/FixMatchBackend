package com.example.fixmatch.repository;

import com.example.fixmatch.entity.Task;
import com.example.fixmatch.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserAndStatus(User user, String status);
}
