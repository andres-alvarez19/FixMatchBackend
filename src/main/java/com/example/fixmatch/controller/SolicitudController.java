package com.example.fixmatch.controller;

import com.example.fixmatch.dto.IdResponse;
import com.example.fixmatch.dto.SolicitudRequest;
import com.example.fixmatch.entity.Solicitud;
import com.example.fixmatch.entity.User;
import com.example.fixmatch.service.SolicitudService;
import com.example.fixmatch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/solicitudes")
@RequiredArgsConstructor
public class SolicitudController {
    private final SolicitudService solicitudService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<IdResponse> create(@RequestBody SolicitudRequest request) {
        User user = userService.findById(request.getUsuarioId()).orElseThrow();
        Solicitud s = solicitudService.create(request, user);
        return ResponseEntity.ok(new IdResponse(s.getId()));
    }

    @PostMapping("/fotos")
    public ResponseEntity<?> uploadFotos(@RequestParam Long solicitudId, @RequestParam("fotos") MultipartFile[] fotos) throws IOException {
        for (MultipartFile foto : fotos) {
            solicitudService.addFoto(solicitudId, foto);
        }
        return ResponseEntity.ok().body("ok");
    }
}
