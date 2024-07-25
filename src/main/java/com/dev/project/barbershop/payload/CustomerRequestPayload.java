package com.dev.project.barbershop.payload;

public record CustomerRequestPayload(String cpf, String name, String whatsapp) implements UserRequestPayload {
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCpf() {
        return cpf;
    }

    @Override
    public String whatsapp() {
        return whatsapp;
    }
}
