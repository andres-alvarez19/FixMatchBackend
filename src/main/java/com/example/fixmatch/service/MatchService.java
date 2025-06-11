package com.example.fixmatch.service;

import com.example.fixmatch.dto.MatchLikeRequest;
import com.example.fixmatch.entity.Job;
import com.example.fixmatch.entity.Match;
import com.example.fixmatch.entity.User;
import com.example.fixmatch.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;

    public Match like(MatchLikeRequest request, Job job, User specialist) {
        Match match = new Match();
        match.setJob(job);
        match.setSpecialist(specialist);
        match.setLikedByClient(request.getLikedByClient());
        match.setLikedBySpecialist(request.getLikedBySpecialist());
        return matchRepository.save(match);
    }

    public List<Match> matchesForSpecialist(User specialist) {
        return matchRepository.findBySpecialist(specialist);
    }
}
