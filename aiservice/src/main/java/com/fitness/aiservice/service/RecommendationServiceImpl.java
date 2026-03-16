package com.fitness.aiservice.service;

import com.fitness.aiservice.dto.RecommendationResponse;
import com.fitness.aiservice.mapper.RecommendationMapper;
import com.fitness.aiservice.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final RecommendationMapper recommendationMapper;

    @Override
    public List<RecommendationResponse> getUserRecommendations(String userId) {
        return recommendationRepository.findAllByUserId(userId).stream()
                .map(recommendationMapper::toRecommendationResponse)
                .toList();
    }

    @Override
    public RecommendationResponse getActivityRecommendations(String activityID) {
        return recommendationRepository.findByActivityId(activityID)
                .map(recommendationMapper::toRecommendationResponse)
                .orElseThrow(() -> new RuntimeException("Activity not found"));
    }
}
