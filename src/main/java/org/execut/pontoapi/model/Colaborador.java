package org.execut.pontoapi.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "colaboradores")
public class Colaborador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String email;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(unique = true, nullable = false)
    private String matricula;

    private String senha;
    private String role = "colaborador";
    private Boolean ativo = true;
    private LocalDateTime criadoEm = LocalDateTime.now();


    @ManyToOne
    @JoinColumn(name = "filial_id")
    private Filial filial;
}