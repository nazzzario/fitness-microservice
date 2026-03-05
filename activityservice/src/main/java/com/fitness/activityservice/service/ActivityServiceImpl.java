package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.ActivityDto;
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

    @Override
    public ActivityDto createActivity(ActivityDto dto) {
        Activity toSave = ActivityMapper.toEntity(dto);
        Activity saved = repository.save(toSave);
        return ActivityMapper.toDto(saved);
    }

    @Override
    public List<ActivityDto> getAllActivities() {
        return repository.findAll()
                .stream()
                .map(ActivityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ActivityDto getActivityById(String id) {
        Activity activity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activity not found: " + id));
        return ActivityMapper.toDto(activity);
    }

    @Override
    public ActivityDto updateActivity(String id, ActivityDto dto) {
        Activity existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activity not found: " + id));

        existing.setUserId(dto.getUserId());
        existing.setType(dto.getType());
        existing.setDuration(dto.getDuration());
        existing.setCaloriesBurned(dto.getCaloriesBurned());
        existing.setStartTime(dto.getStartTime());
        existing.setAdditionalMatrices(dto.getAdditionalMatrices());

        Activity updated = repository.save(existing);
        return ActivityMapper.toDto(updated);
    }

    @Override
    public void deleteActivity(String id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Activity not found: " + id);
        }
        repository.deleteById(id);
    }
}
