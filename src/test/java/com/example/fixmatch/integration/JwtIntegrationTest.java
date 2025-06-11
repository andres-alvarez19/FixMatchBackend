package com.example.fixmatch.integration;

import com.example.fixmatch.dto.AuthResponse;
import com.example.fixmatch.dto.LoginRequest;
import com.example.fixmatch.dto.RegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class JwtIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerLoginAndAccessJobs() throws Exception {
        RegisterRequest register = new RegisterRequest();
        register.setName("Test User");
        register.setEmail("user@example.com");
        register.setPassword("password");

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isOk());

        LoginRequest login = new LoginRequest();
        login.setEmail("user@example.com");
        login.setPassword("password");

        String loginResponse = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        AuthResponse auth = objectMapper.readValue(loginResponse, AuthResponse.class);
        String bearerToken = "Bearer " + auth.getToken();


        mockMvc.perform(get("/jobs")
                .header(HttpHeaders.AUTHORIZATION, "Bearer invalid"))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/jobs")
                .header(HttpHeaders.AUTHORIZATION, bearerToken))
                .andExpect(status().isOk());

        mockMvc.perform(get("/jobs"))
                .andExpect(status().isForbidden());
    }
}
