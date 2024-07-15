package com.dev.project.barbershop.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public Activity createService(ActivityRequestPayload payload) {
        Activity newActivity = new Activity(payload);
        this.activityRepository.save(newActivity);
        return newActivity;
    }

    public List<Activity> getAllActivities() {
        return this.activityRepository.findAll();
    }

    public Optional<Activity> getActivityById(UUID activityId) { return this.activityRepository.findById(activityId); }

    public void updateActivity() {

    }
}
