package com.example.fixmatch.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDate fecha;
    @ManyToOne
    private User usuario;
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL)
    private List<ProyectoFoto> fotos;
}
