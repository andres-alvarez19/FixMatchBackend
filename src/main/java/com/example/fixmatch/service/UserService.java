package com.example.fixmatch.service;

import com.example.fixmatch.dto.RegisterRequest;
import com.example.fixmatch.dto.UserProfileDto;
import com.example.fixmatch.dto.EducationDto;
import com.example.fixmatch.dto.ResumeDto;
import com.example.fixmatch.entity.Role;
import com.example.fixmatch.entity.User;
import com.example.fixmatch.entity.Education;
import com.example.fixmatch.entity.Resume;
import com.example.fixmatch.repository.UserRepository;
import com.example.fixmatch.repository.EducationRepository;
import com.example.fixmatch.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EducationRepository educationRepository;
    private final ResumeRepository resumeRepository;

    public User register(RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        return registerUser(user);
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Optional.ofNullable(user.getRole()).orElse(Role.CLIENT));
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public UserProfileDto toDto(User user) {
        UserProfileDto dto = new UserProfileDto();
        dto.setName(user.getName());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setEmail(user.getEmail());
        dto.setCountryCode(user.getCountryCode());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setLocation(user.getLocation());
        dto.setProfileImage(user.getProfileImage());
        dto.setRating(user.getRating());
        dto.setProjects(user.getProjects());
        dto.setAboutMe(user.getAboutMe());
        if (user.getEducation() != null) {
            java.util.List<EducationDto> eds = new java.util.ArrayList<>();
            for (Education e : user.getEducation()) {
                EducationDto ed = new EducationDto();
                ed.setTitle(e.getTitle());
                ed.setInstitution(e.getInstitution());
                ed.setPeriod(e.getPeriod());
                eds.add(ed);
            }
            dto.setEducation(eds);
        }
        if (user.getResume() != null) {
            ResumeDto r = new ResumeDto();
            r.setName(user.getResume().getName());
            r.setUrl(user.getResume().getUrl());
            r.setSize(user.getResume().getSize());
            if (user.getResume().getDate() != null)
                r.setDate(user.getResume().getDate().toString());
            dto.setResume(r);
        }
        return dto;
    }

    public UserProfileDto updateProfile(User user, UserProfileDto dto) {
        user.setName(dto.getName());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setCountryCode(dto.getCountryCode());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setLocation(dto.getLocation());
        user.setProfileImage(dto.getProfileImage());
        user.setRating(dto.getRating());
        user.setProjects(dto.getProjects());
        user.setAboutMe(dto.getAboutMe());

        // update education
        if (dto.getEducation() != null) {
            if (user.getEducation() != null) {
                educationRepository.deleteAll(user.getEducation());
                user.getEducation().clear();
            } else {
                user.setEducation(new java.util.ArrayList<>());
            }
            for (EducationDto ed : dto.getEducation()) {
                Education e = new Education();
                e.setTitle(ed.getTitle());
                e.setInstitution(ed.getInstitution());
                e.setPeriod(ed.getPeriod());
                e.setUser(user);
                user.getEducation().add(e);
            }
        }

        // update resume
        if (dto.getResume() != null) {
            Resume r = user.getResume();
            if (r == null) {
                r = new Resume();
                r.setUser(user);
            }
            r.setName(dto.getResume().getName());
            r.setUrl(dto.getResume().getUrl());
            r.setSize(dto.getResume().getSize());
            if (dto.getResume().getDate() != null)
                r.setDate(java.time.LocalDate.parse(dto.getResume().getDate()));
            user.setResume(r);
        }

        userRepository.save(user);
        return toDto(user);
    }
}
