package org.execut.pontoapi.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TratamentoDTO {
    private String tipoTratamento;
    private String justificativa;
    private LocalDateTime novaHoraRegistrada;
    private Long idGestorResponsavel;
}