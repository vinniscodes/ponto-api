package org.execut.pontoapi.config;

import org.execut.pontoapi.model.Colaborador;
import org.execut.pontoapi.repository.ColaboradorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder implements CommandLineRunner {

    private final ColaboradorRepository colaboradorRepository;

    public DataSeeder(ColaboradorRepository colaboradorRepository) {
        this.colaboradorRepository = colaboradorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Quando o sistema liga, ele procura a matrícula. Se não achar, ele cria.
        if (colaboradorRepository.findByMatricula("12345").isEmpty()) {
            Colaborador teste = new Colaborador();
            teste.setMatricula("12345");

            // Adicionamos o CPF para o banco de dados não bloquear a criação
            teste.setCpf("000.000.000-00");

            // Preenchendo outros dados básicos para evitar novos erros de 'not-null'
            teste.setNome("Funcionário Teste");
            teste.setEmail("teste@execut.com");
            teste.setSenha("123456");

            colaboradorRepository.save(teste);
            System.out.println("✅ [SISTEMA] Colaborador 12345 criado automaticamente no banco H2 para testes!");
        }
    }
}