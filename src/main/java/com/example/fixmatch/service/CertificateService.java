package com.example.fixmatch.service;

import com.example.fixmatch.dto.CertificateRequest;
import com.example.fixmatch.entity.Certificate;
import com.example.fixmatch.entity.User;
import com.example.fixmatch.repository.CertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateService {
    private final CertificateRepository certificateRepository;

    public Certificate upload(CertificateRequest request, User user) {
        Certificate c = new Certificate();
        c.setType(request.getType());
        c.setFileUrl(request.getFileUrl());
        c.setUploadedBy(user);
        return certificateRepository.save(c);
    }

    public List<Certificate> myCertificates(User user) {
        return certificateRepository.findByUploadedBy(user);
    }

    public void delete(Long id, User user) {
        Certificate certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certificado no encontrado"));
        
        // Verificar que el certificado pertenece al usuario
        if (!certificate.getUploadedBy().getId().equals(user.getId())) {
            throw new RuntimeException("No tienes permisos para eliminar este certificado");
        }
        
        certificateRepository.delete(certificate);
    }
}
