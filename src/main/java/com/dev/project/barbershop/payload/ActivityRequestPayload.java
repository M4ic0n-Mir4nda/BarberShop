package com.dev.project.barbershop.payload;

import java.time.LocalTime;

public record ActivityRequestPayload(String name_service, Double price, LocalTime time_service, String description) {
}
