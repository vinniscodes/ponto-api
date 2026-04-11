package org.execut.pontoapi;

import org.execut.pontoapi.model.Colaborador;
import org.execut.pontoapi.model.Filial;
import org.execut.pontoapi.repository.ColaboradorRepository;
import org.execut.pontoapi.repository.FilialRepository;
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
    public CommandLineRunner seedDatabase(FilialRepository filialRepository, ColaboradorRepository colaboradorRepository) {
        return args -> {
            System.out.println("⏳ Verificando Base de Dados de Tenants...");

            // 1. Cria a Filial da Locar
            if (!filialRepository.existsById("LOCAR123")) {
                Filial locar = new Filial();
                locar.setId("LOCAR123"); locar.setNome("Locar Limpeza Urbana");
                filialRepository.save(locar);
            }

            // 2. Cria a Filial da Locadora
            if (!filialRepository.existsById("RENTAL456")) {
                Filial rental = new Filial();
                rental.setId("RENTAL456"); rental.setNome("Premium Auto Locadora");
                filialRepository.save(rental);
            }

            // 3. Cria a Filial da Jeep
            if (!filialRepository.existsById("JEEP789")) {
                Filial jeep = new Filial();
                jeep.setId("JEEP789"); jeep.setNome("Jeep Automotive");
                filialRepository.save(jeep);
                System.out.println("✅ Filial JEEP789 garantida na Nuvem.");
            }

            // =======================================================
            // 4. A MÁGICA: CRIA O PRIMEIRO ADMINISTRADOR DA JEEP
            // =======================================================
            if (!colaboradorRepository.existsByMatriculaAndTenantId("0000", "JEEP789")) {
                Colaborador adminJeep = new Colaborador();
                adminJeep.setNome("Gestor Master");
                adminJeep.setMatricula("0000"); // A super-senha para entrar no RH
                adminJeep.setCargo("Administrador do Sistema");
                adminJeep.setIsAdmin(true); // Isso é o que libera a tela do RH no App!
                adminJeep.setTenantId("JEEP789");
                colaboradorRepository.save(adminJeep);
                System.out.println("👑 Usuário Gestor Master criado! (Matrícula: 0000)");
            }

            System.out.println("🚀 Sistema EXECUT Enterprise inicializado!");
        };
    }
}