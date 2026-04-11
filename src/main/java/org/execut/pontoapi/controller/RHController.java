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
@CrossOrigin(origins = "*")
public class RHController {

    @Autowired
    private ColaboradorRepository repository;

    // 1. ENDPOINT DE CADASTRO
    @PostMapping("/colaboradores")
    public ResponseEntity<?> cadastrarColaborador(
            @RequestAttribute("tenantId") String tenantId, // Pega a chave direto do Interceptor de Segurança
            @RequestBody Colaborador novoColaborador) {

        System.out.println("Tentativa de cadastro recebida. Matrícula: " + novoColaborador.getMatricula() + " | Filial: " + tenantId);

        // Se o aplicativo não enviar se é admin, define como falso por padrão
        if (novoColaborador.getIsAdmin() == null) {
            novoColaborador.setIsAdmin(false);
        }

        if (repository.existsByMatriculaAndTenantId(novoColaborador.getMatricula(), tenantId)) {
            System.out.println("Bloqueado: Matrícula já existe.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Matrícula já existe nesta filial.");
        }

        novoColaborador.setTenantId(tenantId);
        Colaborador salvo = repository.save(novoColaborador);

        System.out.println("Sucesso! Colaborador salvo no banco.");
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // 2. ENDPOINT DE LOGIN DINÂMICO
    @GetMapping("/login/{matricula}")
    public ResponseEntity<?> realizarLogin(
            @RequestAttribute("tenantId") String tenantId,
            @PathVariable String matricula) {

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

    // 3. ENDPOINT DE LISTAGEM (Para o App e Painel Web)
    @GetMapping("/colaboradores")
    public ResponseEntity<?> listarColaboradores(@RequestAttribute("tenantId") String tenantId) {
        List<Colaborador> lista = repository.findByTenantId(tenantId);
        return ResponseEntity.ok(lista);
    }
}