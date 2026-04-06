package org.execut.pontoapi.dto;

import lombok.Data;

@Data
public class FiltroExportacaoDTO {
    private String dataInicio;
    private String dataFim;
    private String matriculaColaborador;
    private Long idFilial;
}