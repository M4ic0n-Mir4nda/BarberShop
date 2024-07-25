package com.dev.project.barbershop.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    private String cep;

    @Column(name = "public_place", nullable = false)
    private String logradouro;

    @Column(name = "complement", nullable = false)
    private String complemento;

    @Column(name = "neighborhood", nullable = false)
    private String bairro;

    @Column(name = "city", nullable = false)
    private String localidade;

    private String uf;

    private Long ddd;
}
