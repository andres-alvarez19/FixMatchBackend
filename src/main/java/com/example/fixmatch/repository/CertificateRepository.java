package com.example.fixmatch.repository;

import com.example.fixmatch.entity.Certificate;
import com.example.fixmatch.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    List<Certificate> findByUploadedBy(User user);
}
