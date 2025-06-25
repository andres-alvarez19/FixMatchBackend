package com.example.fixmatch.service;

import com.example.fixmatch.entity.Task;
import com.example.fixmatch.entity.User;
import com.example.fixmatch.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    public List<Task> findTasksByUserAndStatus(Long userId, String status) {
        return userService.findById(userId)
                .map(user -> taskRepository.findByUserAndStatus(user, status))
                .orElse(Collections.emptyList());
    }
}
