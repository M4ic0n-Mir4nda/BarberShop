package com.dev.project.barbershop.repository;

import com.dev.project.barbershop.model.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeRepository extends JpaRepository<Employe, String> {
    Optional<Employe> findByCpf(String cpf);
}
