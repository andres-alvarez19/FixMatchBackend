package com.example.fixmatch.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String category;
    private Boolean urgency;
    private String location;
    @ElementCollection
    private List<String> images;
    @ManyToOne
    private User createdBy;
}
