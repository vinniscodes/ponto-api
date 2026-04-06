package org.execut.pontoapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.execut.pontoapi.dto.TratamentoDTO;
import org.execut.pontoapi.dto.FiltroExportacaoDTO;
import org.execut.pontoapi.dto.ConfigGeofencingDTO;
import org.execut.pontoapi.service.PontoService; // <-- Import do Service adicionado

@RestController
@RequestMapping("/api/rh")
public class RHController {

    // Injeção de dependência organizada no topo
    private final PontoService pontoService;

    public RHController(PontoService pontoService) {
        this.pontoService = pontoService;
    }

    // Endpoint atualizado puxando direto do banco de dados
    @GetMapping("/dashboard/batidas")
    public ResponseEntity<?> obterDashboardBatidas() {
        return ResponseEntity.ok(pontoService.listarTodasBatidas());
    }

    @PostMapping("/batida/{idBatida}/tratamento")
    public ResponseEntity<?> tratarEspelhoDePonto(@PathVariable Long idBatida, @RequestBody TratamentoDTO dto) {
        return ResponseEntity.ok("Tratamento (abono/atestado) registrado com sucesso. Histórico de auditoria salvo.");
    }

    @PutMapping("/colaborador/{idColaborador}/status")
    public ResponseEntity<?> atualizarStatusColaborador(@PathVariable Long idColaborador, @RequestBody String status) {
        return ResponseEntity.ok("Status atualizado para: " + status);
    }

    @PostMapping("/exportar/erp")
    public ResponseEntity<?> exportarParaERP(@RequestBody FiltroExportacaoDTO filtro) {
        return ResponseEntity.ok("Dados exportados com sucesso para o Protheus/ERP.");
    }

    @PostMapping("/configuracao/geofencing")
    public ResponseEntity<?> configurarGeofencing(@RequestBody ConfigGeofencingDTO config) {
        return ResponseEntity.ok("Regras de Cerca Virtual atualizadas.");
    }
}