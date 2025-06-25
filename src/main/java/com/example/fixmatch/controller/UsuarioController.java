package com.example.fixmatch.controller;

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
    }
}
