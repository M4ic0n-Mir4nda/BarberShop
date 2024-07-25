package com.dev.project.barbershop.response;

import java.util.UUID;

public record UserData(UUID id, String cpf, String name) {
}
