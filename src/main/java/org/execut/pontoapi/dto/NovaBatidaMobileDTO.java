package org.execut.pontoapi.dto;
import lombok.Data;

@Data
public class NovaBatidaMobileDTO {
    private String tenantId;
    private String matricula;
    private String nomeFuncionario;
    private String dataHora;
    private Double latitude;
    private Double longitude;
    private Boolean isMocked;
    private Boolean offline;
    private String fotoBase64;
    private String deviceId;
}