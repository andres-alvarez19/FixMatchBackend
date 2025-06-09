package com.example.fixmatch.dto;

import lombok.Data;

@Data
public class MatchLikeRequest {
    private Long jobId;
    private Long specialistId;
    private Boolean likedByClient;
    private Boolean likedBySpecialist;
}
