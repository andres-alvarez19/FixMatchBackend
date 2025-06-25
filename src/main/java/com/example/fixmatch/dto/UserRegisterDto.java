package com.example.fixmatch.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserRegisterDto {
    private String nombre;
    private String email;
    private String telefono;
    private String password;
    private String ubicacion;
    private String tipoUsuario; // 'cliente' o 'especialista'
    private List<String> servicios;
} 