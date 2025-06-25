package com.example.fixmatch.controller;

import com.example.fixmatch.dto.IdResponse;
import com.example.fixmatch.dto.ProyectoRequest;
import com.example.fixmatch.entity.Proyecto;
import com.example.fixmatch.entity.User;
import com.example.fixmatch.service.ProyectoService;
import com.example.fixmatch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/proyectos")
@RequiredArgsConstructor
public class ProyectoController {
    private final ProyectoService proyectoService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<IdResponse> create(@RequestBody ProyectoRequest request) {
        User user = userService.findById(request.getUsuarioId()).orElseThrow();
        Proyecto p = proyectoService.create(request, user);
        return ResponseEntity.ok(new IdResponse(p.getId()));
    }

    @PostMapping("/fotos")
    public ResponseEntity<?> uploadFotos(@RequestParam Long projectId, @RequestParam("fotos") MultipartFile[] fotos) throws IOException {
        for (MultipartFile foto : fotos) {
            proyectoService.addFoto(projectId, foto);
        }
        return ResponseEntity.ok().body("ok");
    }
}
