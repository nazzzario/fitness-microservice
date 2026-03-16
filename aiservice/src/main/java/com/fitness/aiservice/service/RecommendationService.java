package com.fitness.aiservice.service;

import com.fitness.aiservice.dto.RecommendationResponse;

import java.util.List;

public interface RecommendationService {
    List<RecommendationResponse> getUserRecommendations(String userId);

    RecommendationResponse getActivityRecommendations(String activityID);
}
