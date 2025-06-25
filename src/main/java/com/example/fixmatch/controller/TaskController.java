package com.example.fixmatch.controller;

import com.example.fixmatch.entity.Task;
import com.example.fixmatch.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/scheduled")
    public ResponseEntity<List<Task>> scheduled(@RequestParam Long userId) {
        return ResponseEntity.ok(taskService.findTasksByUserAndStatus(userId, "En espera"));
    }

    @GetMapping("/completed")
    public ResponseEntity<List<Task>> completed(@RequestParam Long userId) {
        return ResponseEntity.ok(taskService.findTasksByUserAndStatus(userId, "Completada"));
    }
}
