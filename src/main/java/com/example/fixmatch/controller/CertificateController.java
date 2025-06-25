package com.example.fixmatch.controller;

import com.example.fixmatch.dto.CertificateRequest;
import com.example.fixmatch.entity.Certificate;
import com.example.fixmatch.entity.Role;
import com.example.fixmatch.entity.User;
import com.example.fixmatch.service.CertificateService;
import com.example.fixmatch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;
    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('SPECIALIST')")
    public ResponseEntity<Certificate> upload(@RequestBody CertificateRequest request, Principal principal) {
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        return ResponseEntity.ok(certificateService.upload(request, user));
    }

    @GetMapping("/mine")
    public ResponseEntity<List<Certificate>> mine(Principal principal) {
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        return ResponseEntity.ok(certificateService.myCertificates(user));
    }
}
