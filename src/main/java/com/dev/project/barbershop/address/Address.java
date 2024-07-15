package com.dev.project.barbershop.address;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String street;

    @Column(name = "number", nullable = false)
    private int numberAddress;

    private String complement;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String uf;

    public Address(AddressRequestPayload data) {
        this.street = data.street();
        this.numberAddress = data.number();
        this.complement = data.complement();
        this.cep = data.cep();
        this.city = data.city();
        this.uf = data.uf();
    }

}
