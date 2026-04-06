package org.execut.pontoapi.controller;

import org.execut.pontoapi.model.Colaborador;
import org.execut.pontoapi.repository.ColaboradorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthController {

    private final ColaboradorRepository repository;

    public AuthController(ColaboradorRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciais) {
        String login = credenciais.get("login"); // Pode ser email ou matricula
        String password = credenciais.get("password");

        Optional<Colaborador> userOpt;

        // Se o login tem '@', é o Admin tentando entrar por e-mail. Se não, é funcionário por matrícula.
        if (login.contains("@")) {
            userOpt = repository.findByEmail(login);
        } else {
            userOpt = repository.findByMatricula(login);
        }

        if (userOpt.isEmpty() || !userOpt.get().getAtivo()) {
            return ResponseEntity.status(422).body(Map.of("message", "Usuário não encontrado ou inativo"));
        }

        Colaborador user = userOpt.get();

        // Checagem de Senha
        if (!user.getSenha().equals(password)) {
            return ResponseEntity.status(422).body(Map.of("message", "Senha incorreta"));
        }

        return ResponseEntity.ok(Map.of(
                "token", UUID.randomUUID().toString(), // Token provisório
                "user", Map.of(
                        "id", user.getId(),
                        "name", user.getNome(),
                        "role", user.getRole(),
                        "matricula", user.getMatricula()
                )
        ));
    }
}