package com.example.fixmatch.service;

import com.example.fixmatch.dto.CertificateRequest;
import com.example.fixmatch.entity.Certificate;
import com.example.fixmatch.entity.User;
import com.example.fixmatch.repository.CertificateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CertificateServiceTest {
    @Mock
    private CertificateRepository certificateRepository;
    @InjectMocks
    private CertificateService certificateService;

    @Test
    void uploadCreatesCertificateAndSaves() {
        CertificateRequest req = new CertificateRequest();
        req.setType("type");
        req.setFileUrl("url");
        User user = new User();

        when(certificateRepository.save(any(Certificate.class))).thenAnswer(inv -> inv.getArgument(0));

        Certificate c = certificateService.upload(req, user);

        assertEquals("type", c.getType());
        assertEquals("url", c.getFileUrl());
        assertEquals(user, c.getUploadedBy());
        verify(certificateRepository).save(any(Certificate.class));
    }

    @Test
    void myCertificatesReturnsList() {
        User user = new User();
        List<Certificate> list = List.of(new Certificate());
        when(certificateRepository.findByUploadedBy(user)).thenReturn(list);

        List<Certificate> result = certificateService.myCertificates(user);

        assertEquals(list, result);
        verify(certificateRepository).findByUploadedBy(user);
    }
}
