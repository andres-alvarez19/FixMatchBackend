package com.example.fixmatch.dto;

import lombok.Data;

@Data
public class ProyectoRequest {
    private String nombre;
    private String descripcion;
    private String fecha; // YYYY-MM-DD
    private Long usuarioId;
}
