package com.example.fixmatch.dto;

import com.example.fixmatch.entity.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role;
}
