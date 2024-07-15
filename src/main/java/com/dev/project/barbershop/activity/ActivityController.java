package com.dev.project.barbershop.activity;

import com.dev.project.barbershop.exceptions.NotFoundActivitiesException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public void getActivityById() {

    }

    @PutMapping("/{id}/update")
    public void updateActivity() {

    }

    @DeleteMapping("/{id}")
    public void deleteActivity() {

    }
}
