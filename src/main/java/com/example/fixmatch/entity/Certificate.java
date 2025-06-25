package com.example.fixmatch.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String fileUrl;
    @ManyToOne
    private User uploadedBy;
}
