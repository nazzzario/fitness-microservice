package com.fitness.activityservice.controller;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/activities")
@RequiredArgsConstructor
@Tag(name = "activity", description = "Activity management operations")
public class ActivityController {

    private final ActivityService activityService;

    @Operation(summary = "Track activity", description = "Track user activity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Activity created", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ActivityResponse> create(@Valid @RequestBody ActivityRequest request) {
        return ResponseEntity.ok(activityService.trackActivity(request));
    }

    @Operation(summary = "List activities", description = "Returns all activities")
    @ApiResponse(responseCode = "200", description = "Successful retrieval", content = @Content(mediaType = "application/json"))
    @GetMapping
    public ResponseEntity<List<ActivityResponse>> list() {
        return ResponseEntity.ok(activityService.getAllActivities());
    }

    @Operation(summary = "Get activity by ID", description = "Retrieve a specific activity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the activity", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Activity not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ActivityResponse> get(@Parameter(description = "ID of the activity to retrieve") @PathVariable String id) {
        return ResponseEntity.ok(activityService.getActivityById(id));
    }

    @Operation(summary = "Delete activity", description = "Removes an activity by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Activity deleted"),
            @ApiResponse(responseCode = "404", description = "Activity not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(description = "ID of the activity to delete") @PathVariable String id) {
        activityService.deleteActivity(id);
    }
}
