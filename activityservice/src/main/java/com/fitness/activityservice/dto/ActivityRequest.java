package com.fitness.activityservice.dto;

import com.fitness.activityservice.model.ActivityType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ActivityRequest {
    @NotBlank
    @Schema(description = "Identifier of the user who performed the activity", example = "user123")
    private String userId;

    @NotNull
    @Schema(description = "Type of activity", example = "RUNNING")
    private ActivityType type;

    @Schema(description = "Duration in minutes", example = "45")
    private Integer duration;

    @Schema(description = "Calories burned during the activity", example = "250")
    private Integer caloriesBurned;

    @Schema(description = "Start time of the activity in ISO format")
    private LocalDateTime startTime;

    @Schema(description = "Additional arbitrary metrics")
    private Map<String, Object> additionalMatrices;
}
