package com.dev.project.barbershop.model;

import com.dev.project.barbershop.payload.EmployeRequestPayload;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "employe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employe extends User {

    private String password;

    public Employe(EmployeRequestPayload data) {
        this.setName(data.name());
        this.setCpf(data.cpf());
        this.password = data.password();
    }
}
