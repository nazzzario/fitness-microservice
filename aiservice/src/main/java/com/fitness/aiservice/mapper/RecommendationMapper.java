package com.fitness.aiservice.mapper;

import com.fitness.aiservice.dto.RecommendationResponse;
import com.fitness.aiservice.model.Recommendation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecommendationMapper {

    RecommendationResponse toRecommendationResponse(Recommendation recommendation);
}
