package com.example.fixmatch.service;

import com.example.fixmatch.dto.JobRequest;
import com.example.fixmatch.entity.Job;
import com.example.fixmatch.entity.User;
import com.example.fixmatch.repository.JobRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobServiceTest {
    @Mock
    private JobRepository jobRepository;
    @InjectMocks
    private JobService jobService;

    @Test
    void createBuildsJobAndSaves() {
        JobRequest req = new JobRequest();
        req.setDescription("desc");
        req.setCategory("cat");
        req.setUrgency(true);
        req.setLocation("loc");
        req.setImages(List.of("a","b"));
        User user = new User();

        when(jobRepository.save(any(Job.class))).thenAnswer(inv -> inv.getArgument(0));

        Job job = jobService.create(req, user);

        assertEquals("desc", job.getDescription());
        assertEquals("cat", job.getCategory());
        assertTrue(job.getUrgency());
        assertEquals("loc", job.getLocation());
        assertEquals(user, job.getCreatedBy());
        assertEquals(List.of("a","b"), job.getImages());
        verify(jobRepository).save(any(Job.class));
    }

    @Test
    void listAllReturnsAllJobs() {
        List<Job> jobs = List.of(new Job(), new Job());
        when(jobRepository.findAll()).thenReturn(jobs);

        List<Job> result = jobService.listAll();

        assertEquals(jobs, result);
        verify(jobRepository).findAll();
    }
}
