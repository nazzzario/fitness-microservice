package com.fitness.activityservice.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Enumeration of supported activity types")
public enum ActivityType {
    RUNNING,
    WALKING,
    CYCLING,
    SWIMMING,
    WEIGHT_TRAINING,
    YOGA,
    CARDIO,
    STRETCHING
}
