package org.execut.pontoapi.controller;

import org.execut.pontoapi.dto.NovaBatidaMobileDTO;
import org.execut.pontoapi.model.BatidaPonto;
import org.execut.pontoapi.service.PontoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mobile/ponto")
public class PontoMobileController {

    @Autowired
    private PontoService pontoService;

    @PostMapping("/bater")
    public ResponseEntity<?> receberBatidaMobile(
            @RequestAttribute("tenantId") String tenantId, // Puxa do Interceptor! Não precisa ler o Header de novo.
            @RequestBody NovaBatidaMobileDTO payload) {

        // Validação Antifraude Direta
        if (payload.getIsMocked() != null && payload.getIsMocked()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Fraude Detectada: Uso de GPS simulado (Mock Location).");
        }

        try {
            BatidaPonto batidaSalva = pontoService.registrarBatida(payload, tenantId);
            return ResponseEntity.ok(batidaSalva);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar a batida: " + e.getMessage());
        }
    }
}