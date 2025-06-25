package com.example.fixmatch.dto;

import lombok.Data;

import java.util.List;

@Data
public class JobRequest {
    private String description;
    private String category;
    private Boolean urgency;
    private String location;
    private List<String> images;
}
