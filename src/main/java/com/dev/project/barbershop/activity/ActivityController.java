package com.dev.project.barbershop.activity;

import com.dev.project.barbershop.exceptions.CustomException;
import com.dev.project.barbershop.exceptions.NotFoundActivitiesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityRepository repository;

    @PostMapping
    public ResponseEntity<Activity> createService(@RequestBody ActivityRequestPayload payload) {
        Activity newActivity = this.activityService.createService(payload);

        return ResponseEntity.ok(newActivity);
    }

    @GetMapping("/activities")
    public ResponseEntity<List<Activity>> getAllActivities() {
        List<Activity> activitiesList = this.activityService.getAllActivities();

        if (activitiesList.isEmpty()) {
            throw new NotFoundActivitiesException("Nenhum serviço cadastrado");
        }

        return ResponseEntity.ok(activitiesList);
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<Activity> getActivityById(@PathVariable UUID activityId) {
        Optional<Activity> activity = this.activityService.getActivityById(activityId);

        if (activity.isEmpty()) {
            throw new CustomException("Serviço não encontrado");
        }

        return activity.map(ResponseEntity::ok).orElseGet(() -> null);
    }

    @PutMapping("/{activityId}/update")
    public void updateActivity(@PathVariable UUID activityId) {
        Optional<Activity> activity = this.activityService.getActivityById(activityId);

        if (activity.isEmpty()) {
            throw new CustomException("Serviço não encontrado");
        }


    }

    @DeleteMapping("/{id}")
    public void deleteActivity() {

    }
}
