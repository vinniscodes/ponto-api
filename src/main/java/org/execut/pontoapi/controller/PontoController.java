package org.execut.pontoapi.controller;

import org.execut.pontoapi.model.BatidaPonto;
import org.execut.pontoapi.repository.BatidaPontoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ponto")
@CrossOrigin(origins = "*") // Permite que o seu React Web aceda
public class PontoController {

    @Autowired
    private BatidaPontoRepository batidaPontoRepository;

    // Rota usada pelo Painel Administrativo Web para listar as batidas na tabela
    @GetMapping("/listar")
    public ResponseEntity<?> listarBatidasDaEmpresa(@RequestAttribute("tenantId") String tenantId) {

        // Vai buscar apenas as batidas da empresa que fez o pedido (Isolamento de Segurança)
        List<BatidaPonto> batidas = batidaPontoRepository.findByTenantIdOrderByDataHoraDesc(tenantId);

        return ResponseEntity.ok(batidas);
    }
}