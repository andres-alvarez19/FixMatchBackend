package com.example.fixmatch.controller;

import com.example.fixmatch.dto.UserProfileDto;
import com.example.fixmatch.entity.User;
import com.example.fixmatch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> profile(Principal principal) {
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        return ResponseEntity.ok(userService.toDto(user));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserProfileDto> update(@RequestBody UserProfileDto dto, Principal principal) {
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        return ResponseEntity.ok(userService.updateProfile(user, dto));
    }
}
