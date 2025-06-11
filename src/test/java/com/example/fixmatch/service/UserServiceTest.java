package com.example.fixmatch.service;

import com.example.fixmatch.dto.RegisterRequest;
import com.example.fixmatch.entity.Role;
import com.example.fixmatch.entity.User;
import com.example.fixmatch.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @Test
    void registerEncodesPasswordAndSavesUser() {
        RegisterRequest request = new RegisterRequest();
        request.setName("Test");
        request.setEmail("test@example.com");
        request.setPassword("pass");

        when(passwordEncoder.encode("pass")).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User result = userService.register(request);

        assertEquals("Test", result.getName());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("encoded", result.getPassword());
        assertEquals(Role.CLIENT, result.getRole());
        verify(userRepository).save(any(User.class));
        verify(passwordEncoder).encode("pass");
    }

    @Test
    void findByEmailDelegatesToRepository() {
        User user = new User();
        when(userRepository.findByEmail("mail"))
                .thenReturn(Optional.of(user));

        Optional<User> result = userService.findByEmail("mail");

        assertTrue(result.isPresent());
        assertSame(user, result.get());
        verify(userRepository).findByEmail("mail");
    }
}
