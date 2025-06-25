package com.example.fixmatch.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String institution;
    private String period;

    @ManyToOne
    private User user;
}
