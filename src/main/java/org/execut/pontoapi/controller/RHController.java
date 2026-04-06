package org.execut.pontoapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.execut.pontoapi.dto.TratamentoDTO;
import org.execut.pontoapi.dto.FiltroExportacaoDTO;
import org.execut.pontoapi.dto.ConfigGeofencingDTO;
import org.execut.pontoapi.service.PontoService;
import org.execut.pontoapi.model.Colaborador; // Import do Modelo
import org.execut.pontoapi.repository.ColaboradorRepository; // Import do Repositório

import java.util.List;

@RestController
@RequestMapping("/api/rh")
public class RHController {

    private final PontoService pontoService;
    private final ColaboradorRepository colaboradorRepository; // Adicionado para buscar os funcionários

    // Injeção de dependência atualizada
    public RHController(PontoService pontoService, ColaboradorRepository colaboradorRepository) {
        this.pontoService = pontoService;
        this.colaboradorRepository = colaboradorRepository;
    }

    // ==========================================
    // ROTAS NOVAS (Resolve o Erro 404 no Painel)
    // ==========================================

    @GetMapping("/colaboradores")
    public ResponseEntity<List<Colaborador>> listarColaboradores() {
        return ResponseEntity.ok(colaboradorRepository.findAll());
    }

    @DeleteMapping("/colaborador/{id}")
    public ResponseEntity<Void> deletarColaborador(@PathVariable Long id) {
        if(colaboradorRepository.existsById(id)) {
            colaboradorRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // ==========================================
    // SUAS ROTAS ORIGINAIS (Mantidas intactas)
    // ==========================================

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