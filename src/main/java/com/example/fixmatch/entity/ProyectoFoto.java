package com.example.fixmatch.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class ProyectoFoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private LocalDateTime fechaSubida;
    @ManyToOne
    private Proyecto proyecto;
}
