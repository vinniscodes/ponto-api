package org.execut.pontoapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/tenant")
@CrossOrigin(origins = "*")
public class TenantController {

    @GetMapping("/info")
    public ResponseEntity<?> getTenantInfo(@RequestHeader("X-Client-Key") String hashEmpresa) {


        boolean empresaExiste = true;

        if (empresaExiste) {
            Map<String, Object> tenantConfig = new HashMap<>();
            tenantConfig.put("id", "id_da_empresa_no_banco");
            tenantConfig.put("nome", "Nome Dinâmico da Empresa");
            tenantConfig.put("descricao", "Descrição Puxada do Banco");
            tenantConfig.put("icone", "domain");


            Map<String, String> tema = new HashMap<>();
            tema.put("bg", "#111827");
            tema.put("text", "#d1d5db");
            tema.put("primary", "#3b82f6");
            tema.put("card", "#1f2937");
            tema.put("border", "#374151");
            tenantConfig.put("tema", tema);

            return ResponseEntity.ok(tenantConfig);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Chave Inválida");
        }
    }
}