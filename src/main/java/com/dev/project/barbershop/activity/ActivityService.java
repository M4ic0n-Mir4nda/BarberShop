package com.dev.project.barbershop.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
