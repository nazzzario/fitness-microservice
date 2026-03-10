package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface ActivityService {
    void deleteActivity(String id);

    ActivityResponse trackActivity(ActivityRequest request);

    List<ActivityResponse> getAllActivities();

    ActivityResponse getActivityById(String id);

    List<ActivityResponse> getUserActivities(String userID);
}
