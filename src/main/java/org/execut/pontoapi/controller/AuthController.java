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

    @PostMapping("/login")
    public ResponseEntity<?> autenticarPainelWeb(
            @RequestHeader("X-Client-Key") String hashEmpresa,
            @RequestBody Map<String, String> credenciais) {

        String tenantId = hashEmpresa.toUpperCase().trim();
        String matricula = credenciais.get("matricula");

        if (matricula == null) {
            return ResponseEntity.badRequest().body("A matrícula é obrigatória.");
        }

        Optional<Colaborador> user = colaboradorRepository.findByTenantId(tenantId).stream()
                .filter(c -> c.getMatricula().equals(matricula))
                .findFirst();

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Matrícula inválida.");
    }
}