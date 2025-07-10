package com.example.fixmatch.controller;

import javax.management.relation.Role;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.fixmatch.dto.LoginRequest;
import com.example.fixmatch.dto.LoginResponse;
import com.example.fixmatch.dto.UserProfileDto;
import com.example.fixmatch.dto.UserRegisterDto;
import com.example.fixmatch.entity.User;
import com.example.fixmatch.repository.UserRepository;
import com.example.fixmatch.security.JwtTokenProvider;
import com.example.fixmatch.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = userService.findByEmail(request.getEmail()).orElseThrow();
            String token = tokenProvider.generateToken(user.getEmail());
            return ResponseEntity.ok(new LoginResponse(user.getId(), user.getRole().name(), token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(java.util.Map.of("message", "Credenciales inválidas"));
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> getProfile(Principal principal) {
        return userService.getProfileByEmail(principal.getName())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRegisterDto dto) {
        User user = new User();
        user.setName(dto.getNombre());
        user.setEmail(dto.getEmail());
        user.setTelefono(dto.getTelefono());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setLocation(dto.getUbicacion());
        user.setServicios(dto.getServicios());
        user.setRole(com.example.fixmatch.entity.Role.valueOf(dto.getTipoUsuario().toUpperCase()));
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody UserRegisterDto dto) {
        return userService.findById(id)
                .map(user -> {
                    user.setName(dto.getNombre());
                    user.setEmail(dto.getEmail());
                    user.setTelefono(dto.getTelefono());
                    user.setPassword(passwordEncoder.encode(dto.getPassword()));
                    user.setLocation(dto.getUbicacion());
                    user.setServicios(dto.getServicios());
                    user.setRole(com.example.fixmatch.entity.Role.valueOf(dto.getTipoUsuario().toUpperCase()));
                    return ResponseEntity.ok(userService.registerUser(user));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmailAvailability(@RequestParam String email) {
        boolean isAvailable = userService.isEmailAvailable(email);
        return ResponseEntity.ok(java.util.Map.of("available", isAvailable));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserProfileDto> updateProfile(Principal principal, @RequestBody UserProfileDto dto) {
        return userService.findByEmail(principal.getName())
                .map(user -> {
                    UserProfileDto updatedProfile = userService.updateProfile(user, dto);
                    return ResponseEntity.ok(updatedProfile);
                })
                .orElse(ResponseEntity.notFound().build());
    }
} 