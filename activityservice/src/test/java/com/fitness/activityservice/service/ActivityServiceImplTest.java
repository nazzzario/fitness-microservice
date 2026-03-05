package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.ActivityDto;
import com.fitness.activityservice.mapper.ActivityMapper;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.model.ActivityType;
import com.fitness.activityservice.repository.ActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ActivityServiceImplTest {
    @Mock
    private ActivityRepository repository;

    @InjectMocks
    private ActivityServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createActivity_shouldSaveAndReturnDto() {
        ActivityDto dto = ActivityDto.builder()
                .userId("user1")
                .type(ActivityType.RUNNING)
                .duration(30)
                .caloriesBurned(200)
                .startTime(LocalDateTime.now())
                .build();

        Activity saved = ActivityMapper.toEntity(dto);
        saved.setId(UUID.randomUUID().toString());

        when(repository.save(any(Activity.class))).thenReturn(saved);

        ActivityDto result = service.createActivity(dto);

        assertThat(result.getId()).isNotNull();
        assertThat(result.getUserId()).isEqualTo("user1");

        ArgumentCaptor<Activity> captor = ArgumentCaptor.forClass(Activity.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getUserId()).isEqualTo("user1");
    }

    @Test
    void getAllActivities_shouldReturnEmptyList_whenNone() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        List<ActivityDto> all = service.getAllActivities();
        assertThat(all).isEmpty();
    }

    @Test
    void getActivityById_notFound() {
        when(repository.findById("nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getActivityById("nope"))
                .isInstanceOf(RuntimeException.class);
    }
}
