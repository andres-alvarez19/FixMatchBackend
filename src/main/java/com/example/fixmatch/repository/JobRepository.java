package com.example.fixmatch.repository;

import com.example.fixmatch.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
