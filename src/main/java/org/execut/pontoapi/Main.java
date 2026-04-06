package org.execut.pontoapi;

import org.execut.pontoapi.model.Colaborador;
import org.execut.pontoapi.repository.ColaboradorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner run(ColaboradorRepository repository) {
        return args -> {
            // Só tenta criar o usuário se o email ainda não existir no banco
            if (!repository.existsByEmail("admin@empresa.com")) {
                Colaborador admin = new Colaborador();
                admin.setNome("Administrador Supremo");
                admin.setEmail("admin@empresa.com");

                // ⚠️ NOVA LINHA OBRIGATÓRIA: O banco agora exige CPF para todos!
                admin.setCpf("00000000000");

                admin.setSenha("Execut@123");
                admin.setMatricula("1233");
                admin.setRole("admin");

                repository.save(admin);
                System.out.println("✅ Usuário Admin criado no banco com sucesso!");
            } else {
                System.out.println("👍 Usuário Admin já existe. Pulando criação.");
            }
        };
    }
}