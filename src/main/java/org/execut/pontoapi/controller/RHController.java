package org.execut.pontoapi.controller;

import org.execut.pontoapi.model.Colaborador;
import org.execut.pontoapi.repository.ColaboradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rh")
@CrossOrigin(origins = "*") // Essencial para rodar no navegador Web
public class RHController {

    @Autowired
    private ColaboradorRepository repository;

    @PostMapping("/colaboradores")
    public ResponseEntity<?> cadastrarColaborador(
            @RequestHeader("X-Client-Key") String hashEmpresa, // Lê direto do Header!
            @RequestBody Colaborador novoColaborador) {

        String tenantId = hashEmpresa.toUpperCase().trim();
        System.out.println("Tentativa de cadastro. Matrícula: " + novoColaborador.getMatricula() + " | Filial: " + tenantId);

        if (novoColaborador.getIsAdmin() == null) {
            novoColaborador.setIsAdmin(false);
        }

        if (repository.existsByMatriculaAndTenantId(novoColaborador.getMatricula(), tenantId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Matrícula já existe nesta filial.");
        }

        novoColaborador.setTenantId(tenantId);
        Colaborador salvo = repository.save(novoColaborador);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/login/{matricula}")
    public ResponseEntity<?> realizarLogin(
            @RequestHeader("X-Client-Key") String hashEmpresa, // Lê direto do Header!
            @PathVariable String matricula) {

        String tenantId = hashEmpresa.toUpperCase().trim();
        List<Colaborador> colaboradores = repository.findByTenantId(tenantId);
        Optional<Colaborador> funcionario = colaboradores.stream()
                .filter(c -> c.getMatricula().equals(matricula))
                .findFirst();

        if (funcionario.isPresent()) {
            return ResponseEntity.ok(funcionario.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credencial não localizada nesta filial.");
        }
    }

    @GetMapping("/colaboradores")
    public ResponseEntity<?> listarColaboradores(@RequestHeader("X-Client-Key") String hashEmpresa) {
        String tenantId = hashEmpresa.toUpperCase().trim();
        List<Colaborador> lista = repository.findByTenantId(tenantId);
        return ResponseEntity.ok(lista);
    }
}