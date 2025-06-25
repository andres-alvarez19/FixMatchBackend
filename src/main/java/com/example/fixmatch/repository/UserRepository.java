package com.example.fixmatch.repository;

import com.example.fixmatch.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< ftr_solucionar-errores-de-métodos-no-encontrados_2025-06-25
public interface UserRepository extends JpaRepository<User, Long> {
=======
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
>>>>>>> develop
}
