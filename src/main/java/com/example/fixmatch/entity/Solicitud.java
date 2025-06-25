package com.example.fixmatch.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Solicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String especialidad;
    private String nombreSolicitud;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    @ManyToOne
    private User usuario;
    @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL)
    private List<SolicitudFoto> fotos;
}
