package com.example.fixmatch.controller;

<<<<<<< ftr_solucionar-errores-de-métodos-no-encontrados_2025-06-25
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
=======
import com.example.fixmatch.dto.CreateUsuarioRequest;
import com.example.fixmatch.dto.IdResponse;
import com.example.fixmatch.entity.User;
import com.example.fixmatch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<IdResponse> create(@RequestBody CreateUsuarioRequest request) {
        User user = new User();
        user.setName(request.getNombre());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // raw, service will encode
        user.setRole(request.getRole());
        user.setTelefono(request.getTelefono());
        user.setUbicacion(request.getUbicacion());
        if (request.getEspecialista() != null) {
            user.setServicios(request.getEspecialista().getServicios());
        }
        User saved = userService.registerUser(user);
        return ResponseEntity.ok(new IdResponse(saved.getId()));
>>>>>>> develop
    }
}
