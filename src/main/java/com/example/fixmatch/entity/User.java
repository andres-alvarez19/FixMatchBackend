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
}
