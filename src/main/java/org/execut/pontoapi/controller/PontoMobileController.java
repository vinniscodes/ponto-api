package org.execut.pontoapi.controller;

import org.execut.pontoapi.dto.NovaBatidaMobileDTO;
import org.execut.pontoapi.service.PontoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mobile/ponto")
public class PontoMobileController {

    private final PontoService pontoService;

    public PontoMobileController(PontoService pontoService) {
        this.pontoService = pontoService;
    }

    @PostMapping("/bater")
    public ResponseEntity<?> registrarBatidaMobile(@RequestBody NovaBatidaMobileDTO captura) {
        pontoService.processarNovaBatida(captura);
        return ResponseEntity.ok().body("{\"message\": \"Ponto registrado com sucesso!\", \"hash\": \"ABC-123-HASH\"}");
    }
}