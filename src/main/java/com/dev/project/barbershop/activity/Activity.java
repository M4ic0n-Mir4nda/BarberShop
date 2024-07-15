package com.dev.project.barbershop.activity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String nameService;

    @Column(nullable = false)
    private Double price;

    @Column(name = "time_service", nullable = false)
    private LocalTime timeService;

    @Column(nullable = false)
    private String description;

    public Activity(ActivityRequestPayload data) {
        this.nameService = data.name_service();
        this.price = data.price();
        this.timeService = LocalTime.parse(data.time_service().format(DateTimeFormatter.ofPattern("HH:mm")));
        this.description = data.description();
    }
}
