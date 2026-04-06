package org.execut.pontoapi.controller;

import org.execut.pontoapi.model.BatidaPonto;
import org.execut.pontoapi.model.Colaborador;
import org.execut.pontoapi.repository.BatidaRepository;
import org.execut.pontoapi.repository.ColaboradorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/ponto")
@CrossOrigin(origins = "*")
public class PontoController {

    private final BatidaRepository batidaRepository;
    private final ColaboradorRepository colaboradorRepository;

    public PontoController(BatidaRepository batidaRepository, ColaboradorRepository colaboradorRepository) {
        this.batidaRepository = batidaRepository;
        this.colaboradorRepository = colaboradorRepository;
    }

    // 📍 ROTA PARA O COLABORADOR BATER PONTO (Com GPS)
    @PostMapping("/bater")
    public ResponseEntity<?> baterPonto(@RequestBody BatidaPonto requestData, @RequestHeader("Usuario-ID") Long userId) {
        Colaborador user = colaboradorRepository.findById(userId).orElseThrow();

        BatidaPonto novaBatida = new BatidaPonto();
        novaBatida.setColaborador(user);
        novaBatida.setHoraBatida(LocalDateTime.now());
        novaBatida.setLatitude(requestData.getLatitude());
        novaBatida.setLongitude(requestData.getLongitude());

        batidaRepository.save(novaBatida);
        return ResponseEntity.ok(Map.of("message", "Ponto registrado com sucesso na localização atual!"));
    }

    // 👔 ROTA ADMIN: CRIAR COLABORADOR
    @PostMapping("/admin/colaborador")
    public ResponseEntity<?> criarColaborador(@RequestBody Colaborador novoUser) {
        // A mágica: A senha vira os primeiros 6 dígitos do CPF (remove pontuação primeiro)
        String cpfLimpo = novoUser.getCpf().replaceAll("[^0-9]", "");
        String senhaGerada = cpfLimpo.substring(0, 6);

        novoUser.setSenha(senhaGerada);
        novoUser.setRole("colaborador");

        colaboradorRepository.save(novoUser);
        return ResponseEntity.ok(Map.of("message", "Colaborador criado! Senha de acesso: " + senhaGerada));
    }
}