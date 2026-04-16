package org.execut.pontoapi.controller;

import org.execut.pontoapi.model.BatidaPonto;
import org.execut.pontoapi.repository.BatidaPontoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ponto")
@CrossOrigin(origins = "*")
public class PontoController {

    @Autowired
    private BatidaPontoRepository batidaPontoRepository;

    @GetMapping("/listar")
    public ResponseEntity<?> listarBatidasDaEmpresa(@RequestHeader("X-Client-Key") String hashEmpresa) {
        String tenantId = hashEmpresa.toUpperCase().trim();
        List<BatidaPonto> batidas = batidaPontoRepository.findByTenantIdOrderByDataHoraDesc(tenantId);

        return ResponseEntity.ok(batidas);
    }
}