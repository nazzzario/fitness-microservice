package com.fitness.activityservice.controller;

import com.fitness.activityservice.dto.ActivityDto;
import com.fitness.activityservice.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/activities")
@RequiredArgsConstructor
@Tag(name = "activity", description = "Activity management operations")
public class ActivityController {
    private final ActivityService service;

    @Operation(summary = "Create a new activity", description = "Creates an activity for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Activity created", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ActivityDto> create(@Valid @RequestBody ActivityDto dto) {
        ActivityDto created = service.createActivity(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @Operation(summary = "List activities", description = "Returns all activities")
    @ApiResponse(responseCode = "200", description = "Successful retrieval", content = @Content(mediaType = "application/json"))
    @GetMapping
    public List<ActivityDto> list() {
        return service.getAllActivities();
    }

    @Operation(summary = "Get activity by ID", description = "Retrieve a specific activity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the activity", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Activity not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ActivityDto get(@Parameter(description = "ID of the activity to retrieve") @PathVariable String id) {
        return service.getActivityById(id);
    }

    @Operation(summary = "Update an activity", description = "Updates an existing activity by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activity updated", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Activity not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ActivityDto update(@Parameter(description = "ID of the activity to update") @PathVariable String id, @Valid @RequestBody ActivityDto dto) {
        return service.updateActivity(id, dto);
    }

    @Operation(summary = "Delete activity", description = "Removes an activity by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Activity deleted"),
            @ApiResponse(responseCode = "404", description = "Activity not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(description = "ID of the activity to delete") @PathVariable String id) {
        service.deleteActivity(id);
    }
}
