package org.execut.pontoapi.dto;

import lombok.Data;

@Data
public class ConfigGeofencingDTO {
    private boolean habilitarRestricao;
    private Double latitudeSede;
    private Double longitudeSede;
    private Integer raioPermitidoMetros;
}