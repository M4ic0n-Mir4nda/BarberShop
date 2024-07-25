package com.dev.project.barbershop.response;

import java.util.UUID;

public record CustomerData(UUID id, String cpf, String name, String whatsapp) {
}
