package com.dev.project.barbershop.establishment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EstablishmentRepository extends JpaRepository<Establishment, UUID> {
    public Establishment findEstablishmentByCnpj(String cnpj);
}
