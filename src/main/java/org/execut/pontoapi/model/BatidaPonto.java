package org.execut.pontoapi.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_batidas_ponto")
public class BatidaPonto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Colaborador colaborador;

    // RN-001: Nome antigo mantido para o React, mas o banco rejeita qualquer 'update' nesta coluna.
    @Column(nullable = false, updatable = false)
    private LocalDateTime horaBatida;

    @Column(updatable = false)
    private String deviceId;

    @Column(updatable = false)
    private boolean isRegistroOffline;

    @Column(updatable = false)
    private Double latitude;

    @Column(updatable = false)
    private Double longitude;

    @Column(updatable = false)
    private String caminhoFotoSelfie;

    private LocalDateTime dataLimiteRetencaoLgpd;

    private boolean dadosSensiveisAnonimizados = false;

    private boolean exportadoParaErp = false;
}