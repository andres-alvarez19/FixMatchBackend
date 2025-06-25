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
<<<<<<< ftr_solucionar-errores-de-métodos-no-encontrados_2025-06-25

    private String nombre;
    private String email;
    private String telefono;
    private String ubicacion;

    @ElementCollection
    @CollectionTable(name = "user_servicios", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "servicio")
    private List<String> servicios;
=======
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String avatar;
    private Double reputation;
    private String dateOfBirth;
    private String countryCode;
    private String phoneNumber;
    private String location;
    private String profileImage;
    private Double rating;
    private Integer projects;
    private String aboutMe;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Education> education;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Resume resume;

    @OneToMany(mappedBy = "uploadedBy")
    private java.util.List<Certificate> certificates;
>>>>>>> develop
}
