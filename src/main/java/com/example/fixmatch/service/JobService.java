package com.example.fixmatch.service;

import com.example.fixmatch.dto.JobRequest;
import com.example.fixmatch.entity.Job;
import com.example.fixmatch.entity.User;
import com.example.fixmatch.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;

    public Job create(JobRequest request, User user) {
        Job job = new Job();
        job.setDescription(request.getDescription());
        job.setCategory(request.getCategory());
        job.setUrgency(request.getUrgency());
        job.setLocation(request.getLocation());
        job.setImages(request.getImages());
        job.setCreatedBy(user);
        return jobRepository.save(job);
    }

    public List<Job> listAll() {
        return jobRepository.findAll();
    }

    public Job findById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }
}
