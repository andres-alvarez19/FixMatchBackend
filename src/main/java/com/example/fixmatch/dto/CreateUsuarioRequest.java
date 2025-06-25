package com.example.fixmatch.dto;

import com.example.fixmatch.entity.Role;
import lombok.Data;
import java.util.List;

@Data
public class CreateUsuarioRequest {
    private String tipoUsuario; // "cliente" or "especialista"
    private String nombre;
    private String email;
    private String telefono;
    private String password;
    private String ubicacion;
    private EspecialistaData especialista;

    @Data
    public static class EspecialistaData {
        private List<String> servicios;
    }

    public Role getRole() {
        if ("especialista".equalsIgnoreCase(tipoUsuario)) {
            return Role.SPECIALIST;
        }
        return Role.CLIENT;
    }
}
