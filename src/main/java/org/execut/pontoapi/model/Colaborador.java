package org.execut.pontoapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "colaboradores")
@Data
public class Colaborador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String matricula;

    @Column(nullable = false)
    private String cargo;

    @Column(nullable = false)
    private Boolean isAdmin;


    @Column(nullable = false, name = "tenant_id")
    private String tenantId;
}