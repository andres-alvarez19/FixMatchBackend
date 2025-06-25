package com.example.fixmatch.dto;

import lombok.Data;

import java.util.List;

@Data
public class JobResponse {
    private Long id;
    private String title;
    private String location;
    private String distance;
    private String description;
    private List<String> images;
}
