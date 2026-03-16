package com.fitness.aiservice.dto;

import java.time.LocalDateTime;
import java.util.List;

public record RecommendationResponse(
        String id,
        String activityId,
        String userId,
        String activityType,
        String recommendation,
        List<String>improvements,
        List<String> suggestions,
        List<String> safety,
        LocalDateTime createdAt
) {
}
