package com.dev.project.barbershop.model;

import com.dev.project.barbershop.payload.CustomerRequestPayload;
import com.dev.project.barbershop.payload.EmployeRequestPayload;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends User {

    private String whatsapp;

    public Customer(CustomerRequestPayload data) {
        this.setName(data.getName());
        this.setCpf(data.getCpf());
        this.whatsapp = data.whatsapp();
    }
}
