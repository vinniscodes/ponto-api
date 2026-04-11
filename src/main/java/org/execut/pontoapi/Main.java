package org.execut.pontoapi;

import org.execut.pontoapi.model.Filial;
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

    /**
     * O "Data Seeder": Este método corre automaticamente sempre que o Spring Boot arranca.
     * Ele verifica se as filiais já existem na base de dados. Se não existirem, ele cria-as.
     * Isto garante que o Interceptor não bloqueia a aplicação na primeira utilização.
     */
    @Bean
    public CommandLineRunner seedDatabase(FilialRepository filialRepository) {
        return args -> {
            System.out.println("⏳ A verificar Base de Dados de Tenants...");

            // 1. Cria a Filial da Locar (Limpeza)
            if (!filialRepository.existsById("LOCAR123")) {
                Filial locar = new Filial();
                locar.setId("LOCAR123");
                locar.setNome("Locar Limpeza Urbana");
                locar.setDescricao("Divisão de Gestão de Resíduos");
                locar.setIcone("recycle-variant");
                locar.setCorBg("#022c22");
                locar.setCorText("#a7f3d0");
                locar.setCorPrimary("#f59e0b");
                locar.setCorCard("#064e3b");
                locar.setCorBorder("#047857");
                filialRepository.save(locar);
                System.out.println("✅ Filial LOCAR123 criada com sucesso na Nuvem.");
            }

            // 2. Cria a Filial da Locadora
            if (!filialRepository.existsById("RENTAL456")) {
                Filial rental = new Filial();
                rental.setId("RENTAL456");
                rental.setNome("Premium Auto Locadora");
                rental.setDescricao("Gestão de Frotas de Luxo");
                rental.setIcone("shield-car");
                rental.setCorBg("#082f49");
                rental.setCorText("#bae6fd");
                rental.setCorPrimary("#38bdf8");
                rental.setCorCard("#0c4a6e");
                rental.setCorBorder("#0284c7");
                filialRepository.save(rental);
                System.out.println("✅ Filial RENTAL456 criada com sucesso na Nuvem.");
            }

            // 3. Cria a Filial da Jeep
            if (!filialRepository.existsById("JEEP789")) {
                Filial jeep = new Filial();
                jeep.setId("JEEP789");
                jeep.setNome("Jeep Automotive");
                jeep.setDescricao("Planta de Manufatura Automotiva");
                jeep.setIcone("jeepney");
                jeep.setCorBg("#111827");
                jeep.setCorText("#d1d5db");
                jeep.setCorPrimary("#84cc16");
                jeep.setCorCard("#1f2937");
                jeep.setCorBorder("#374151");
                filialRepository.save(jeep);
                System.out.println("✅ Filial JEEP789 criada com sucesso na Nuvem.");
            }

            System.out.println("🚀 Sistema EXECUT Enterprise inicializado e pronto para receber batidas!");
        };
    }
}