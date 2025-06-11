package com.example.fixmatch.controller;

import com.example.fixmatch.dto.MatchLikeRequest;
import com.example.fixmatch.entity.Job;
import com.example.fixmatch.entity.Match;
import com.example.fixmatch.entity.User;
import com.example.fixmatch.service.JobService;
import com.example.fixmatch.service.MatchService;
import com.example.fixmatch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/match")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;
    private final JobService jobService;
    private final UserService userService;

    @PostMapping("/like")
    public ResponseEntity<Match> like(@RequestBody MatchLikeRequest request, Principal principal) {
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        Job job = jobService.findById(request.getJobId());
        User specialist = userService.findById(request.getSpecialistId()).orElseThrow();
        return ResponseEntity.ok(matchService.like(request, job, specialist));
    }

    @GetMapping("/matches")
    public ResponseEntity<List<Match>> myMatches(Principal principal) {
        User specialist = userService.findByEmail(principal.getName()).orElseThrow();
        return ResponseEntity.ok(matchService.matchesForSpecialist(specialist));
    }
}
