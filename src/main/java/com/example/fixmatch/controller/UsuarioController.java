package com.example.fixmatch.controller;

import com.example.fixmatch.entity.User;
import com.example.fixmatch.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UserRepository userRepository;

    public UsuarioController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User saved = userRepository.save(user);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updated) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setNombre(updated.getNombre());
                    user.setEmail(updated.getEmail());
                    user.setTelefono(updated.getTelefono());
                    user.setUbicacion(updated.getUbicacion());
                    user.setServicios(updated.getServicios());
                    User saved = userRepository.save(user);
                    return ResponseEntity.ok(saved);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<User> allUsers() {
        return userRepository.findAll();
    }
}
