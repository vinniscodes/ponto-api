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

    // =========================================================
    // 1. ENDPOINT DE LOGIN DINÂMICO (Usado pelo App)
    // =========================================================
    @GetMapping("/login/{matricula}")
    public ResponseEntity<?> realizarLogin(
            @RequestHeader("X-Client-Key") String hashEmpresa,
            @PathVariable String matricula) {

        // Em um SaaS real, a Hash em maiúsculo VIRA o Tenant ID oficial no banco
        String tenantId = hashEmpresa.toUpperCase().trim();

        // Busca todos os funcionários daquela filial e procura a matrícula
        List<Colaborador> colaboradores = repository.findByTenantId(tenantId);
        Optional<Colaborador> funcionario = colaboradores.stream()
                .filter(c -> c.getMatricula().equals(matricula))
                .findFirst();

        if (funcionario.isPresent()) {
            return ResponseEntity.ok(funcionario.get()); // Retorna os dados + isAdmin para o App
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credencial não localizada nesta filial.");
        }
    }

    // =========================================================
    // 2. ENDPOINT DE CADASTRO (Adicionar Colaborador para Sempre)
    // =========================================================
    @PostMapping("/colaboradores")
    public ResponseEntity<?> cadastrarColaborador(
            @RequestHeader("X-Client-Key") String hashEmpresa,
            @RequestBody Colaborador novoColaborador) {

        String tenantId = hashEmpresa.toUpperCase().trim();

        // Trava de segurança contra duplicidade na mesma empresa
        if (repository.existsByMatriculaAndTenantId(novoColaborador.getMatricula(), tenantId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Matrícula já existe nesta filial.");
        }

        // Carimba o funcionário com a Hash da empresa e salva no PostgreSQL
        novoColaborador.setTenantId(tenantId);
        Colaborador salvo = repository.save(novoColaborador);

        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // =========================================================
    // 3. ENDPOINT DE LISTAGEM (Painel Admin Web e Mobile)
    // =========================================================
    @GetMapping("/colaboradores")
    public ResponseEntity<?> listarColaboradores(@RequestHeader("X-Client-Key") String hashEmpresa) {

        String tenantId = hashEmpresa.toUpperCase().trim();
        List<Colaborador> lista = repository.findByTenantId(tenantId);

        return ResponseEntity.ok(lista);
    }
}