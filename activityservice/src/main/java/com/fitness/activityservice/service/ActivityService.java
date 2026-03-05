package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.ActivityDto;

import java.util.List;

public interface ActivityService {
    ActivityDto createActivity(ActivityDto dto);
    List<ActivityDto> getAllActivities();
    ActivityDto getActivityById(String id);
    ActivityDto updateActivity(String id, ActivityDto dto);
    void deleteActivity(String id);
}
