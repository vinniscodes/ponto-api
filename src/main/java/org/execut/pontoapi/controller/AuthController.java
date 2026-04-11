package org.execut.pontoapi.controller;

import org.execut.pontoapi.model.Colaborador;
import org.execut.pontoapi.repository.ColaboradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    // Novo endpoint de Login compatível com a Nuvem Multi-Tenant
    @PostMapping("/login")
    public ResponseEntity<?> autenticarPainelWeb(
            @RequestAttribute("tenantId") String tenantId,
            @RequestBody Map<String, String> credenciais) {

        String matricula = credenciais.get("matricula");

        if (matricula == null) {
            return ResponseEntity.badRequest().body("A matrícula é obrigatória.");
        }

        // Procura a matrícula APENAS dentro dos funcionários desta filial
        Optional<Colaborador> user = colaboradorRepository.findByTenantId(tenantId).stream()
                .filter(c -> c.getMatricula().equals(matricula))
                .findFirst();

        if (user.isPresent()) {
            // Opcional: Se quiser que SÓ administradores entrem no Painel Web, pode validar assim:
            // se (!user.get().getIsAdmin()) return ResponseEntity.status(403).body("Acesso negado.");

            return ResponseEntity.ok(user.get());
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Matrícula inválida para esta filial.");
    }
}