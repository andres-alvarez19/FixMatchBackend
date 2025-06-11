package com.example.fixmatch.service;

import com.example.fixmatch.dto.MatchLikeRequest;
import com.example.fixmatch.entity.Job;
import com.example.fixmatch.entity.Match;
import com.example.fixmatch.entity.User;
import com.example.fixmatch.repository.MatchRepository;
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
class MatchServiceTest {
    @Mock
    private MatchRepository matchRepository;
    @InjectMocks
    private MatchService matchService;

    @Test
    void likeCreatesMatchAndSaves() {
        MatchLikeRequest req = new MatchLikeRequest();
        req.setLikedByClient(true);
        req.setLikedBySpecialist(false);
        Job job = new Job();
        User specialist = new User();

        when(matchRepository.save(any(Match.class))).thenAnswer(inv -> inv.getArgument(0));

        Match match = matchService.like(req, job, specialist);

        assertEquals(job, match.getJob());
        assertEquals(specialist, match.getSpecialist());
        assertTrue(match.getLikedByClient());
        assertFalse(match.getLikedBySpecialist());
        verify(matchRepository).save(any(Match.class));
    }

    @Test
    void matchesForSpecialistReturnsList() {
        User specialist = new User();
        List<Match> matches = List.of(new Match());
        when(matchRepository.findBySpecialist(specialist)).thenReturn(matches);

        List<Match> result = matchService.matchesForSpecialist(specialist);

        assertEquals(matches, result);
        verify(matchRepository).findBySpecialist(specialist);
    }
}
