package org.execut.pontoapi.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NovaBatidaMobileDTO {
    private String matriculaColaborador;
    private LocalDateTime dataHoraNtp;
    private Double latitude;
    private Double longitude;
    private String fotoBase64;
    private String deviceId;
    private boolean isOffline;
}