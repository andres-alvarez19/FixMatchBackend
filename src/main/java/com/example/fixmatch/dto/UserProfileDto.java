package com.example.fixmatch.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserProfileDto {
    private String name;
    private String dateOfBirth;
    private String email;
    private String countryCode;
    private String phoneNumber;
    private String location;
    private String profileImage;
    private Double rating;
    private Integer projects;
    private String aboutMe;
    private List<EducationDto> education;
    private ResumeDto resume;
}
