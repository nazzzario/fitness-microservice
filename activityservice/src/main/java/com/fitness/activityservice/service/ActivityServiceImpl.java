package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.exception.ResourceNotFoundException;
import com.fitness.activityservice.mapper.ActivityMapper;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository repository;
    private final ActivityMapper activityMapper;

    @Override
    public ActivityResponse trackActivity(ActivityRequest dto) {
        Activity toSave = activityMapper.toEntity(dto);
        Activity saved = repository.save(toSave);
        return activityMapper.toResponse(saved);
    }

    @Override
    public List<ActivityResponse> getAllActivities() {
        return repository.findAll().stream()
                .map(activityMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ActivityResponse getActivityById(String id) {
        return activityMapper.toResponse(repository.findById(id).orElseThrow());
    }

    @Override
    public List<ActivityResponse> getUserActivities(String userID) {
        return repository.findByUserId(userID).stream()
                .map(activityMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteActivity(String id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Activity not found: " + id);
        }
        repository.deleteById(id);
    }
}
