package com.dev.project.barbershop.model;

import com.dev.project.barbershop.payload.EmployeRequestPayload;
import com.dev.project.barbershop.payload.UserRequestPayload;
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
        this.setName(data.getName());
        this.setCpf(data.getCpf());
        this.password = data.password();
    }
}
