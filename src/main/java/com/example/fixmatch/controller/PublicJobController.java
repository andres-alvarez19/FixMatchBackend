package com.example.fixmatch.controller;

import com.example.fixmatch.dto.JobResponse;
import com.example.fixmatch.entity.Job;
import com.example.fixmatch.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/trabajos")
@RequiredArgsConstructor
public class PublicJobController {

    private final JobService jobService;

    @GetMapping
    public ResponseEntity<List<JobResponse>> list() {
        List<JobResponse> jobs = jobService.listAll().stream().map(this::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(jobs);
    }

    private JobResponse toResponse(Job job) {
        JobResponse response = new JobResponse();
        response.setId(job.getId());
        response.setTitle(job.getCategory());
        response.setLocation(job.getLocation());
        response.setDistance("0 km");
        response.setDescription(job.getDescription());
        response.setImages(job.getImages());
        return response;
    }
}
