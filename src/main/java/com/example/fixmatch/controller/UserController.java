package com.example.fixmatch.controller;

import javax.management.relation.Role;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.fixmatch.dto.UserRegisterDto;
import com.example.fixmatch.entity.User;
import com.example.fixmatch.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRegisterDto dto) {
        User user = new User();
        user.setName(dto.getNombre());
        user.setEmail(dto.getEmail());
        user.setTelefono(dto.getTelefono());
        user.setPassword(dto.getPassword());
        user.setLocation(dto.getUbicacion());
        user.setServicios(dto.getServicios());
        // Solución: importar correctamente el enum Role desde el paquete entity y usarlo aquí
        user.setRole(com.example.fixmatch.entity.Role.valueOf(dto.getTipoUsuario().toUpperCase()));
        return ResponseEntity.ok(userRepository.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody UserRegisterDto dto) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(dto.getNombre());
                    user.setEmail(dto.getEmail());
                    user.setTelefono(dto.getTelefono());
                    user.setPassword(dto.getPassword());
                    user.setLocation(dto.getUbicacion());
                    user.setServicios(dto.getServicios());
                    user.setRole(com.example.fixmatch.entity.Role.valueOf(dto.getTipoUsuario().toUpperCase()));
                    return ResponseEntity.ok(userRepository.save(user));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
} 