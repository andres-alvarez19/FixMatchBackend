package com.example.fixmatch.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String telefono;
    private String ubicacion;

    @ElementCollection
    @CollectionTable(name = "user_servicios", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "servicio")
    private List<String> servicios;
}
