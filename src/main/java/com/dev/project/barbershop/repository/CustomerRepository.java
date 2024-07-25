package com.dev.project.barbershop.repository;

import com.dev.project.barbershop.model.Customer;
import com.dev.project.barbershop.model.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByCpf(String cpf);
}
