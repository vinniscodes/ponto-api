package org.execut.pontoapi.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "batidas_ponto")
@Data
public class BatidaPonto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "tenant_id")
    private String tenantId;

    @Column(nullable = false)
    private String matricula;

    private String nomeFuncionario;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    private Double latitude;
    private Double longitude;

    // Dados de Auditoria e Antifraude (Item 15 e 16 do PDF)
    private Boolean isMocked;
    private Boolean offline;
    private String deviceId;

    @Column(columnDefinition = "TEXT") // TEXT é usado porque a foto Base64 é uma string gigante
    private String fotoBase64;
}