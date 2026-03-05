package com.fitness.activityservice.mapper;

import com.fitness.activityservice.dto.ActivityDto;
import com.fitness.activityservice.model.Activity;

public class ActivityMapper {
    public static ActivityDto toDto(Activity activity) {
        if (activity == null) {
            return null;
        }
        return ActivityDto.builder()
                .id(activity.getId())
                .userId(activity.getUserId())
                .type(activity.getType())
                .duration(activity.getDuration())
                .caloriesBurned(activity.getCaloriesBurned())
                .startTime(activity.getStartTime())
                .additionalMatrices(activity.getAdditionalMatrices())
                .createdAt(activity.getCreatedAt())
                .updatedAt(activity.getUpdatedAt())
                .build();
    }

    public static Activity toEntity(ActivityDto dto) {
        if (dto == null) {
            return null;
        }
        return Activity.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .type(dto.getType())
                .duration(dto.getDuration())
                .caloriesBurned(dto.getCaloriesBurned())
                .startTime(dto.getStartTime())
                .additionalMatrices(dto.getAdditionalMatrices())
                .build();
    }
}
