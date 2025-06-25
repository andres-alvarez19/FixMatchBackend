package com.example.fixmatch.dto;

import lombok.Data;

@Data
public class SolicitudRequest {
    private String especialidad;
    private String nombreSolicitud;
    private String descripcion;
    private Long usuarioId;
}
