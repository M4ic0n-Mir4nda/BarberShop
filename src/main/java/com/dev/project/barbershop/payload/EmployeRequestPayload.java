package com.dev.project.barbershop.payload;

public record EmployeRequestPayload(String cpf, String name, String password) implements UserRequestPayload {
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCpf() {
        return cpf;
    }

    @Override
    public String password() {
        return password;
    }
}
