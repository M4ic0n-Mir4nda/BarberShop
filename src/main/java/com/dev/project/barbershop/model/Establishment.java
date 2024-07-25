package com.dev.project.barbershop.model;

import com.dev.project.barbershop.payload.EstablishmentRequestPayload;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "establishment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Establishment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String nameEstablishment;

    @Column(nullable = false)
    private String whatsapp;

    @Column(nullable = false, unique = true)
    private String cnpj;

    @ManyToOne
    @JoinColumn(name = "cep", nullable = false)
    private Address address;

    public Establishment(EstablishmentRequestPayload data) {
        this.email = data.email();
        this.password = data.password();
        this.nameEstablishment = data.name();
        this.whatsapp = data.whatsapp();
        this.cnpj = data.cnpj();
    }
}
